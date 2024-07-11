package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import article.model.ArticleContent;
import jdbc.JdbcUtil;

public class ArticleContentDao {

	// 게시글 내용을 데이터베이스에 삽입하는 메서드
	public ArticleContent insert(Connection conn, ArticleContent content) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			// 게시글 내용 삽입 SQL 준비
			pstmt = conn.prepareStatement("insert into article_content (article_no, content) values (?,?)");
			pstmt.setLong(1, content.getNumber()); // 게시글 번호 설정
			pstmt.setString(2, content.getContent()); // 게시글 내용 설정
			int insertedCount = pstmt.executeUpdate(); // SQL 실행 및 삽입된 행의 수 반환
			if (insertedCount > 0) {
				return content; // 삽입된 내용 반환
			} else {
				return null; // 삽입 실패 시 null 반환
			}
		} finally {
			// 리소스 정리
			JdbcUtil.close(pstmt);
		}
	}

	// 게시글 번호로 특정 게시글 내용을 조회하는 메서드
	public ArticleContent selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 게시글 내용 조회 SQL 준비
			pstmt = conn.prepareStatement("select * from article_content where article_no = ?");
			pstmt.setInt(1, no); // 게시글 번호 설정
			rs = pstmt.executeQuery(); // SQL 실행 및 결과 반환
			ArticleContent content = null;
			if (rs.next()) {
				content = new ArticleContent(rs.getInt("article_no"), rs.getString("content"));
				// 결과를 ArticleContent 객체로 변환

			}
			return content; // 조회된 게시글 내용 반환
		} finally {
			// 리소스 정리
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 게시글 내용을 수정하는 메서드
	public int update(Connection conn, int no, String content) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("update article_content set content = ? where article_no = ?")) {
			pstmt.setString(1, content); // 새로운 내용 설정
			pstmt.setInt(2, no); // 게시글 번호 설정
			return pstmt.executeUpdate(); // SQL 실행 및 수정된 행의 수 반환
		}
	}

	// 게시글 내용을 삭제하는 메서드
	public int delete(Connection conn, int articleNo2) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from article_content where article_no = ?")) {
			pstmt.setInt(1, articleNo2); // 삭제할 게시글 번호 설정
			return pstmt.executeUpdate(); // SQL 실행 및 삭제된 행의 수 반환
		}
	}
}
