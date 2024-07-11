package member.service;

public class InvalidPasswordException extends RuntimeException {
	// 현재 암호가 일치하지 않는경우
	// (암호를 변경할 때 보통 현재 암호와 변경할 암호를 함께 입력하는데, 이때 현재 암호가 일치하지 않으면 암호 변경에 실패한다.)
}
