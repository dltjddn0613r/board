package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class ChangePasswordService {
	// MemberDao 객체를 생성하여 데이터베이스 작업에 사용합니다.
	private MemberDao memberDao = new MemberDao();

	/**
	 * 사용자의 비밀번호를 변경합니다.
	 *
	 * @param userId 사용자 ID
	 * @param curPwd 현재 비밀번호
	 * @param newPwd 새 비밀번호
	 * @throws MemberNotFoundException  사용자가 존재하지 않을 때 발생
	 * @throws InvalidPasswordException 현재 비밀번호가 일치하지 않을 때 발생
	 */
	public void changePassword(String userId, String curPwd, String newPwd) {
		Connection conn = null;// 데이터베이스 연결을 가져옵니다.
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);// 자동 커밋을 비활성화합니다.

			// 주어진 사용자 ID로 사용자를 조회합니다.
			Member member = memberDao.selectById(conn, userId);
			if (member == null) {// 사용자가 존재하지 않으면 예외를 던집니다.
				throw new MemberNotFoundException();
			}
			if (!member.matchPassword(curPwd)) {// 현재 비밀번호가 일치하지 않으면 예외를 던집니다.
				throw new InvalidPasswordException();
			}

			member.changePassword(newPwd);// 새 비밀번호로 비밀번호를 변경합니다.
			memberDao.update(conn, member); // 변경된 내용을 데이터베이스에 업데이트합니다.
			conn.commit();// 트랜잭션을 커밋합니다.
		} catch (SQLException e) {
			JdbcUtil.rollback(conn); // SQL 예외가 발생하면 롤백합니다.
			throw new RuntimeException(e); // 런타임 예외를 던집니다.
		} finally {
			JdbcUtil.close(conn);// 데이터베이스 연결을 닫습니다.
		}
	}
}
