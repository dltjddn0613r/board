package member.service;

import java.util.Map;

public class JoinRequest {
	// 회원가입 기능 처리 JoinService가 회원가입 기능을 구현 할때 필요한 요청 데이터를담는 클래스

	private String id;
	private String name;
	private String password;
	private String confirmPassword;// 비밀번호 확인

	public String getId() {// 회원 아이디 getter
		return id;
	}

	public void setId(String id) {// 회원 아이디 setter
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	// 비밀번호와 비밀번호 확인이 일치하는지 확인하는 메서드
	public boolean isPasswordEqualToConfirm() {
		return password != null && password.equals(confirmPassword);// 널이 아니고 둘이 같으면 트루
	}// (true: 일치, false: 불일치)

	// 입력 데이터 유효성 검사 메서드
	public void validate(Map<String, Boolean> errors) {
		checkEmpty(errors, id, "id");
		checkEmpty(errors, name, "name");
		checkEmpty(errors, password, "password");
		checkEmpty(errors, confirmPassword, "confirmPassword");

		if (!errors.containsKey("confirmPassword")) {
			if (!isPasswordEqualToConfirm()) {
				errors.put("notMatch", Boolean.TRUE);
			}
		} // !isPasswordEqualToConfirm()비밀번호와 비밀번호 확인이 일치하지 않는 경우
	}

	private void checkEmpty(Map<String, Boolean> errors, String value, String fieldName) {
		if (value == null || value.isEmpty())
			errors.put(fieldName, Boolean.TRUE);
	}// 값이 비어있는지 확인하고 오류 맵에 오류 정보를 추가하는 메서드
}// 필드 이름(fieldName)을 키로 하여 true 값을 errors 맵에 추가합니다.
