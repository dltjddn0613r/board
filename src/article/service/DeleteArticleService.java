package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import auth.service.DeleteRequest;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class DeleteArticleService {
	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();

	public void delete(DeleteRequest deleteReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			int articleNo1 = articleDao.delete(conn, deleteReq.getArticleNumber());
			int articleNo2 = contentDao.delete(conn, deleteReq.getArticleNumber());

			articleDao.delete(conn, articleNo1);
			contentDao.delete(conn, articleNo2);

			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}