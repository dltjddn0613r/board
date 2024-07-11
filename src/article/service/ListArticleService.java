package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.connection.ConnectionProvider;

public class ListArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private int size = 10; // 페이지당 게시글 수

	/**
	 * 특정 페이지의 ArticlePage 객체를 반환하는 메서드
	 * 
	 * @param pageNum 가져올 페이지 번호
	 * @return ArticlePage 객체
	 */
	public ArticlePage getArticlePage(int pageNum) {
		int firstRow = 0; // 페이지의 첫 번째 행 번호
		int endRow = 0; // 페이지의 마지막 행 번호
		List<Article> content = null; // 페이지의 게시글 목록

		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = articleDao.selectCount(conn); // 전체 게시글 수 가져오기

			if (total > 0) {
				// 페이지에 따른 첫 번째 행과 마지막 행 계산
				firstRow = (pageNum - 1) * size + 1;
				endRow = firstRow + size - 1;

				// 해당 페이지의 게시글 목록 가져오기
				content = articleDao.select(conn, firstRow, endRow, size);
			}

			// ArticlePage 객체 생성하여 반환
			return new ArticlePage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
