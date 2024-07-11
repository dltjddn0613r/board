package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import member.model.Member;

public class MemberDao {// 클래스는 데이터베이스에 접근하여 회원 정보를 조회, 등록, 수정, 삭제하는 기능을 제공합니다.
//데이터베이스와 관련된 SQL 쿼리를 실행하고, 그 결과를 Java 객체로 변환하여 서비스 클래스에 전달합니다.
	// 일반적으로 DAO 클래스는 데이터베이스 트랜잭션을 시작하고, 커밋 또는 롤백하는 기능도 담당합니다.
	public Member selectById(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from member where memberid = ?");
			pstmt.setString(1, id);// 첫 번째 매개변수(인덱스 1)에 memberId를 설정

			rs = pstmt.executeQuery();// 쿼리 실행 후 결과 집합 받기
			Member member = null;
			if (rs.next()) {
				// ResultSet에서 각 필드 값 가져와 Member 객체 생성
				member = new Member(rs.getString("memberid"), rs.getString("name"), rs.getString("password"),
						toDate(rs.getTimestamp("regdate")));
			}
			return member; // 조회된 회원 객체 반환 (존재하지 않으면 null)
		} finally {// 자원 해제
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	private Date toDate(Timestamp date) {
		return date == null ? null : new Date(date.getTime());
	}

	// 회원 정보를 데이터베이스에 삽입하는 메서드
	public void insert(Connection conn, Member mem) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into member values(?,?,?,?)")) {
			pstmt.setString(1, mem.getId());
			pstmt.setString(2, mem.getName());
			pstmt.setString(3, mem.getPassword());
			pstmt.setTimestamp(4, new Timestamp(mem.getRegDate().getTime()));
			pstmt.executeUpdate();
		}
	}

//회원 정보를 업데이트하는 메서드
	// MemberDao의 update()메서드는 member테이블의 memberid필드가 member.getId()와 같은 레코드의
	// name과password필드 값을 변경한다
	public void update(Connection conn, Member member) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("update member set name = ?, password = ? where memberid = ?")) {
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getId());
			pstmt.executeUpdate();
		}
	}
}
