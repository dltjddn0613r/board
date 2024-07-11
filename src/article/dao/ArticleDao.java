package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import article.model.Article;
import article.model.Writer;
import jdbc.JdbcUtil;

public class ArticleDao {

	// 게시글을 데이터베이스에 삽입하는 메서드
	public Article insert(Connection conn, Article article) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 게시글 삽입 SQL 준비
			pstmt = conn.prepareStatement(
					"insert into article " + "(article_no, writer_id, writer_name, title, regdate, moddate, read_cnt) "
							+ "values (article_no_seq.nextval, ?, ?, ?, ?, ?, 0)");

			// SQL 파라미터 설정
			pstmt.setString(1, article.getWriter().getId());
			pstmt.setString(2, article.getWriter().getName());
			pstmt.setString(3, article.getTitle());
			pstmt.setTimestamp(4, toTimestamp(article.getRegDate()));
			pstmt.setTimestamp(5, toTimestamp(article.getModifiedDate()));

			// SQL 실행 및 삽입된 행의 수 반환
			int insertedCount = pstmt.executeUpdate();

			if (insertedCount > 0) {
				// 게시글 번호를 조회하는 SQL 실행
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT article_no " + "FROM (SELECT article_no " + "      FROM article "
						+ "      ORDER BY article_no DESC) " + "WHERE ROWNUM = 1");

				// 삽입된 게시글 번호를 받아서 새로운 Article 객체 반환
				if (rs.next()) {
					Integer newNo = rs.getInt(1);
					return new Article(newNo, article.getWriter(), article.getTitle(), article.getRegDate(),
							article.getModifiedDate(), 0);
				}
			}
			return null; // 삽입 실패 시 null 반환
		} finally {
			// 리소스 정리
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}

	// Date 객체를 Timestamp 객체로 변환하는 메서드
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	// 데이터베이스에 저장된 게시글의 개수를 조회하는 메서드
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 게시글 개수 조회 SQL 실행
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article");
			if (rs.next()) {
				return rs.getInt(1); // 조회된 개수 반환
			}
			return 0; // 게시글이 없을 경우 0 반환
		} finally {
			// 리소스 정리
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	// 특정 범위의 게시글을 조회하는 메서드
	public List<Article> select(Connection conn, int firstRow, int endRow, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 게시글 범위 조회 SQL 준비
			pstmt = conn.prepareStatement("select * from (select rownum as rnum, a.* from "
					+ "(select * from article order by article_no desc) a " + "where rownum <= ?) where rnum >= ?");

			// SQL 파라미터 설정
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, firstRow);

			// SQL 실행 및 결과를 리스트에 추가
			rs = pstmt.executeQuery();
			List<Article> result = new ArrayList<>();
			while (rs.next()) {
				result.add(convertArticle(rs));
			}
			return result; // 결과 리스트 반환
		} finally {
			// 리소스 정리
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// ResultSet의 현재 행을 Article 객체로 변환하는 메서드
	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getInt("article_no"), new Writer(rs.getString("writer_id"), rs.getString("writer_name")),
				rs.getString("title"), toDate(rs.getTimestamp("regdate")), toDate(rs.getTimestamp("moddate")),
				rs.getInt("read_cnt"));
	}

	// Timestamp 객체를 Date 객체로 변환하는 메서드
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	// 게시글 번호로 특정 게시글을 조회하는 메서드
	public Article selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 게시글 조회 SQL 준비
			pstmt = conn.prepareStatement("select * from article where article_no = ?");
			pstmt.setInt(1, no);

			// SQL 실행 및 결과 반환
			rs = pstmt.executeQuery();
			Article article = null;
			if (rs.next()) {
				article = convertArticle(rs);// 앞서 게시글 목록 조회 기능을 구현할 때 추가한 것.
			}
			return article; // 조회된 게시글 반환
		} finally {
			// 리소스 정리
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 게시글의 조회수를 증가시키는 메서드 조회수 카운트
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("update article set read_cnt = read_cnt + 1 where article_no = ?")) {
			pstmt.setInt(1, no); // 게시글 번호 설정
			pstmt.executeUpdate(); // SQL 실행
		}
	}

	// 게시글의 제목을 수정하는 메서드
	public int update(Connection conn, int no, String title) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("UPDATE article SET title = ?, moddate = SYSDATE WHERE article_no = ?")) {
			pstmt.setString(1, title); // PreparedStatement에 제목을 설정합니다.
			pstmt.setInt(2, no); // PreparedStatement에 게시글 번호를 설정합니다.
			return pstmt.executeUpdate(); // SQL을 실행하여 업데이트된 행의 수를 반환합니다.
		}
	}

	// 게시글을 삭제하는 메서드
	public int delete(Connection conn, int articleNo1) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from article where article_no = ?")) {
			pstmt.setInt(1, articleNo1); // 삭제할 게시글 번호 설정
			return pstmt.executeUpdate(); // SQL 실행 및 삭제된 행의 수 반환
		}
	}
}
