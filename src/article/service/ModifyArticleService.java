package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ModifyArticleService {

	private ArticleDao articleDao = new ArticleDao(); // ArticleDao 객체

	private ArticleContentDao contentDao = new ArticleContentDao();
	// ArticleContentDao 객체

	/**
	 * 게시글 수정 서비스를 제공하는 클래스입니다.
	 */

	public void modify(ModifyRequest modReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(); // 데이터베이스 연결
			conn.setAutoCommit(false); // 자동 커밋을 false로 설정하여 트랜잭션 시작

			// 수정할 게시글 정보 조회
			Article article = articleDao.selectById(conn, modReq.getArticleNumber());

			// 조회된 게시글이 null이면 예외 발생
			if (article == null) {
				throw new ArticleNotFoundException();
			}

			// 수정 권한 검사
			if (!canModify(modReq.getUserId(), article)) {
				throw new PermissionDeniedException();
			}

			// 게시글 제목 업데이트
			articleDao.update(conn, modReq.getArticleNumber(), modReq.getTitle());

			// 게시글 내용 업데이트
			contentDao.update(conn, modReq.getArticleNumber(), modReq.getContent());

			conn.commit(); // 트랜잭션 커밋
		} catch (SQLException e) {
			JdbcUtil.rollback(conn); // SQLException 발생 시 롤백 처리
			throw new RuntimeException(e); // 런타임 예외로 전환하여 상위로 던짐
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn); // 수정 권한이 없는 경우 롤백 처리
			throw e; // PermissionDeniedException을 상위로 던짐
		} finally {
			JdbcUtil.close(conn); // 연결 닫기
		}
	}

	/**
	 * 게시글 수정 권한을 검사합니다.
	 * 
	 * @param modfyingUserId 수정을 시도한 사용자의 ID
	 * @param article        조회된 게시글 객체
	 * @return 수정 권한 여부 (true: 수정 가능, false: 수정 불가능)
	 */
	private boolean canModify(String modfyingUserId, Article article) {
		return article.getWriter().getId().equals(modfyingUserId);
		// 게시글 작성자의 ID와 수정을 시도한 사용자의 ID 비교
	}
}
