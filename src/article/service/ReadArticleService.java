package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.connection.ConnectionProvider;

/**
 * 특정 게시글 조회 및 게시글 내용을 제공하는 서비스 클래스입니다.
 */
public class ReadArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();

	/**
	 * 주어진 게시글 번호에 해당하는 게시글과 게시글 내용을 조회합니다.
	 * 
	 * @param articleNum        조회할 게시글 번호
	 * @param increaseReadCount 조회수 증가 여부 (true: 조회수 증가, false: 조회수 증가 안 함)
	 * @return ArticleData 객체 (게시글 정보와 내용을 담고 있는 객체)
	 * @throws ArticleNotFoundException        조회된 게시글이 없을 경우 발생하는 예외
	 * @throws ArticleContentNotFoundException 조회된 게시글 내용이 없을 경우 발생하는 예외
	 * @throws RuntimeException                데이터베이스 처리 중 발생하는 예외
	 */
	public ArticleData getArticle(int articleNum, boolean increaseReadCount) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 게시글 조회
			Article article = articleDao.selectById(conn, articleNum);
			if (article == null) {
				throw new ArticleNotFoundException();
			}

			// 게시글 내용 조회
			ArticleContent content = contentDao.selectById(conn, articleNum);
			if (content == null) {
				throw new ArticleContentNotFoundException();
			}

			// 조회수 증가 처리
			if (increaseReadCount) {
				articleDao.increaseReadCount(conn, articleNum);
			}

			// ArticleData 객체 생성하여 반환
			return new ArticleData(article, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
