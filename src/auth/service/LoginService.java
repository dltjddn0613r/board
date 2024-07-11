package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class LoginService {
//사용자가 입력한 아이디와 암호가 올바른지 검사한다.
//아이디와 암호가 일치하면 로그인한 사용자 정보를 담은 User객체를 리턴
// 아이디화 암호가 잘못된 경우 LoginFailException을 발생시킨다
	private MemberDao memberDao = new MemberDao();

	/**
	 * MemberDao를 이용해서 아이디에 해당하는 회원 데이터가 존재하는지 확인한다. 회원 데이터가 존재하지 않거나 암호가 일치하지 않으면
	 * LoginFailException을 발생시킨다. 암호가 일치하면 회원 아이디와 이름을 담은 User 객체를 생성해서 리턴한다.
	 *
	 * @param id       사용자가 입력한 아이디
	 * @param password 사용자가 입력한 비밀번호
	 * @return 로그인 성공 시 회원 아이디와 이름을 담은 User 객체
	 * @throws LoginFailException 로그인 실패 시 발생하는 예외
	 */

	public User login(String id, String password) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			// MemberDao를 사용하여 데이터베이스에서 입력된 아이디에 해당하는 회원 정보를 조회한다.
			Member member = memberDao.selectById(conn, id);
			if (member == null) {
				throw new LoginFailException();
			} // 조회된 회원 정보가 없으면 로그인 실패 예외를 발생시킨다.
			if (!member.matchPassword(password)) {
				throw new LoginFailException();
			} // 입력된 비밀번호와 회원 정보의 비밀번호가 일치하지 않으면 로그인 실패 예외를 발생시킨다.
			return new User(member.getId(), member.getName());
			// 로그인 성공 시 회원 아이디와 이름을 가지고 있는 User 객체를 생성하여 반환한다.
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} // 데이터베이스 접근 중 예외가 발생하면 RuntimeException으로 감싸서 던진다.
	}
}
