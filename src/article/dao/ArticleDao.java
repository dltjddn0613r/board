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

	// 寃뚯떆湲��쓣 �뜲�씠�꽣踰좎씠�뒪�뿉 �궫�엯�븯�뒗 硫붿꽌�뱶
	public Article insert(Connection conn, Article article) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 寃뚯떆湲� �궫�엯 SQL 以�鍮�
			pstmt = conn.prepareStatement(
					"insert into article " + "(article_no, writer_id, writer_name, title, regdate, moddate, read_cnt) "
							+ "values (article_no_seq.nextval, ?, ?, ?, ?, ?, 0)");

			// SQL �뙆�씪誘명꽣 �꽕�젙
			pstmt.setString(1, article.getWriter().getId());
			pstmt.setString(2, article.getWriter().getName());
			pstmt.setString(3, article.getTitle());
			pstmt.setTimestamp(4, toTimestamp(article.getRegDate()));
			pstmt.setTimestamp(5, toTimestamp(article.getModifiedDate()));

			// SQL �떎�뻾 諛� �궫�엯�맂 �뻾�쓽 �닔 諛섑솚
			int insertedCount = pstmt.executeUpdate();

			if (insertedCount > 0) {
				// 寃뚯떆湲� 踰덊샇瑜� 議고쉶�븯�뒗 SQL �떎�뻾
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT article_no " + "FROM (SELECT article_no " + "      FROM article "
						+ "      ORDER BY article_no DESC) " + "WHERE ROWNUM = 1");

				// �궫�엯�맂 寃뚯떆湲� 踰덊샇瑜� 諛쏆븘�꽌 �깉濡쒖슫 Article 媛앹껜 諛섑솚
				if (rs.next()) {
					Integer newNo = rs.getInt(1);
					return new Article(newNo, article.getWriter(), article.getTitle(), article.getRegDate(),
							article.getModifiedDate(), 0);
				}
			}
			return null; // �궫�엯 �떎�뙣 �떆 null 諛섑솚
		} finally {
			// 由ъ냼�뒪 �젙由�
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}

	// Date 媛앹껜瑜� Timestamp 媛앹껜濡� 蹂��솚�븯�뒗 硫붿꽌�뱶
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	// �뜲�씠�꽣踰좎씠�뒪�뿉 ���옣�맂 寃뚯떆湲��쓽 媛쒖닔瑜� 議고쉶�븯�뒗 硫붿꽌�뱶
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 寃뚯떆湲� 媛쒖닔 議고쉶 SQL �떎�뻾
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article");
			if (rs.next()) {
				return rs.getInt(1); // 議고쉶�맂 媛쒖닔 諛섑솚
			}
			return 0; // 寃뚯떆湲��씠 �뾾�쓣 寃쎌슦 0 諛섑솚
		} finally {
			// 由ъ냼�뒪 �젙由�
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	// �듅�젙 踰붿쐞�쓽 寃뚯떆湲��쓣 議고쉶�븯�뒗 硫붿꽌�뱶
	public List<Article> select(Connection conn, int firstRow, int endRow, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 寃뚯떆湲� 踰붿쐞 議고쉶 SQL 以�鍮�
			pstmt = conn.prepareStatement("select * from (select rownum as rnum, a.* from "
					+ "(select * from article order by article_no desc) a " + "where rownum <= ?) where rnum >= ?");

			// SQL �뙆�씪誘명꽣 �꽕�젙
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, firstRow);

			// SQL �떎�뻾 諛� 寃곌낵瑜� 由ъ뒪�듃�뿉 異붽�
			rs = pstmt.executeQuery();
			List<Article> result = new ArrayList<>();
			while (rs.next()) {
				result.add(convertArticle(rs));
			}
			return result; // 寃곌낵 由ъ뒪�듃 諛섑솚
		} finally {
			// 由ъ냼�뒪 �젙由�
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// ResultSet�쓽 �쁽�옱 �뻾�쓣 Article 媛앹껜濡� 蹂��솚�븯�뒗 硫붿꽌�뱶
	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getInt("article_no"), new Writer(rs.getString("writer_id"), rs.getString("writer_name")),
				rs.getString("title"), toDate(rs.getTimestamp("regdate")), toDate(rs.getTimestamp("moddate")),
				rs.getInt("read_cnt"));
	}

	// Timestamp 媛앹껜瑜� Date 媛앹껜濡� 蹂��솚�븯�뒗 硫붿꽌�뱶
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	// 寃뚯떆湲� 踰덊샇濡� �듅�젙 寃뚯떆湲��쓣 議고쉶�븯�뒗 硫붿꽌�뱶
	public Article selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 寃뚯떆湲� 議고쉶 SQL 以�鍮�
			pstmt = conn.prepareStatement("select * from article where article_no = ?");
			pstmt.setInt(1, no);

			// SQL �떎�뻾 諛� 寃곌낵 諛섑솚
			rs = pstmt.executeQuery();
			Article article = null;
			if (rs.next()) {
				article = convertArticle(rs);// �븵�꽌 寃뚯떆湲� 紐⑸줉 議고쉶 湲곕뒫�쓣 援ы쁽�븷 �븣 異붽��븳 寃�.
			}
			return article; // 議고쉶�맂 寃뚯떆湲� 諛섑솚
		} finally {
			// 由ъ냼�뒪 �젙由�
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 寃뚯떆湲��쓽 議고쉶�닔瑜� 利앷��떆�궎�뒗 硫붿꽌�뱶 議고쉶�닔 移댁슫�듃
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("update article set read_cnt = read_cnt + 1 where article_no = ?")) {
			pstmt.setInt(1, no); // 寃뚯떆湲� 踰덊샇 �꽕�젙
			pstmt.executeUpdate(); // SQL �떎�뻾
		}
	}

	// 寃뚯떆湲��쓽 �젣紐⑹쓣 �닔�젙�븯�뒗 硫붿꽌�뱶
	public int update(Connection conn, int no, String title) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("UPDATE article SET title = ?, moddate = SYSDATE WHERE article_no = ?")) {
			pstmt.setString(1, title); // PreparedStatement�뿉 �젣紐⑹쓣 �꽕�젙�빀�땲�떎.
			pstmt.setInt(2, no); // PreparedStatement�뿉 寃뚯떆湲� 踰덊샇瑜� �꽕�젙�빀�땲�떎.
			return pstmt.executeUpdate(); // SQL�쓣 �떎�뻾�븯�뿬 �뾽�뜲�씠�듃�맂 �뻾�쓽 �닔瑜� 諛섑솚�빀�땲�떎.
		}
	}

	// 寃뚯떆湲��쓣 �궘�젣�븯�뒗 硫붿꽌�뱶
	public int delete(Connection conn, int articleNo1) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from article where article_no = ?")) {
			pstmt.setInt(1, articleNo1); // �궘�젣�븷 寃뚯떆湲� 踰덊샇 �꽕�젙
			return pstmt.executeUpdate(); // SQL �떎�뻾 諛� �궘�젣�맂 �뻾�쓽 �닔 諛섑솚
		}
	}
}
