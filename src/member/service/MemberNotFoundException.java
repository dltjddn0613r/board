package member.service;

public class MemberNotFoundException extends RuntimeException {
//암호를 변경할 회원데이터가 존재하지 않는경우 MemberNotFoundException는 이것
	// 현재 암호가 일치하지 않는경우 InvalidPasswordException의 경우
	// (암호를 변경할 때 보통 현재 암호와 변경할 암호를 함께 입력하는데, 이때 현재 암호가 일치하지 않으면 암호 변경에 실패한다.)
}
