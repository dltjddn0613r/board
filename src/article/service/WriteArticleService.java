package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/**
 * 게시글 작성 서비스를 제공하는 클래스
 */
public class WriteArticleService {

	private ArticleDao articleDao = new ArticleDao(); // ArticleDao 객체
	private ArticleContentDao contentDao = new ArticleContentDao(); // ArticleContentDao 객체

	/**
	 * 게시글을 작성하는 메서드
	 * 
	 * @param req WriteRequest 객체로부터 작성 요청 정보를 받아옴
	 * @return 저장된 게시글 번호
	 */
	public Integer write(WriteRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(); // Connection 생성
			conn.setAutoCommit(false); // 트랜잭션 시작

			Article article = toArticle(req);
			// WriteRequest 객체를 Article 객체로 변환

			Article savedArticle = articleDao.insert(conn, article);
			// ArticleDao를 사용하여 게시글 저장

			if (savedArticle == null) {
				throw new RuntimeException("fail to insert article");
			}

			ArticleContent content = new ArticleContent(savedArticle.getNumber(), req.getContent());
			// ArticleContent 생성

			ArticleContent savedContent = contentDao.insert(conn, content);
			// ArticleContentDao를 사용하여 게시글 내용 저장
			if (savedContent == null) {
				throw new RuntimeException("fail to insert article_content");
			}

			conn.commit(); // 트랜잭션 커밋

			return savedArticle.getNumber(); // 저장된 게시글 번호 반환
		} catch (SQLException e) {
			JdbcUtil.rollback(conn); // SQLException 발생 시 롤백
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn); // RuntimeException 발생 시 롤백
			throw e;
		} finally {
			JdbcUtil.close(conn); // Connection 닫기
		}
	}

	/**
	 * WriteRequest 객체를 Article 객체로 변환하는 메서드
	 * 
	 * @param req WriteRequest 객체
	 * @return 변환된 Article 객체
	 */
	private Article toArticle(WriteRequest req) {
		Date now = new Date(); // 현재 날짜 생성
		return new Article(null, req.getWriter(), req.getTitle(), now, now, 0); // Article 객체 생성하여 반환
	}
}
