package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class JoinService {// 회원 가입 기능을 제공하는 서비스 클래스입니다.

	private MemberDao memberDao = new MemberDao();// MemberDao 객체 생성

	public void join(JoinRequest joinReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();// 데이터베이스 연결
			conn.setAutoCommit(false);// 트랜잭션 시작

			// 아이디 중복 체크
			Member member = memberDao.selectById(conn, joinReq.getId());
			if (member != null) {
				JdbcUtil.rollback(conn);// 롤백 처리
				throw new DuplicateIdException();// 중복된 아이디 예외 던지기
			}
			// 회원 정보 등록
			memberDao.insert(conn, new Member(joinReq.getId(), joinReq.getName(), joinReq.getPassword(), new Date()));
			conn.commit();// 트랜잭션 커밋
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);// 예외 발생 시 롤백 처리
			throw new RuntimeException(e);// 런타임 예외로 전환하여 상위 레벨로 전파
		} finally {
			JdbcUtil.close(conn);// 연결 자원 닫기
		}
	}
}
