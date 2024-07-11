package article.service;

import java.util.Map;

public class ModifyRequest {

	private String userId; // 수정을 요청한 사용자의 ID
	private int articleNumber; // 수정할 게시글 번호
	private String title; // 수정할 제목
	private String content; // 수정할 내용

	/**
	 * 수정 요청 객체를 생성합니다.
	 * 
	 * @param userId        수정을 요청한 사용자의 ID
	 * @param articleNumber 수정할 게시글 번호
	 * @param title         수정할 제목
	 * @param content       수정할 내용
	 */
	public ModifyRequest(String userId, int articleNumber, String title, String content) {
		this.userId = userId;
		this.articleNumber = articleNumber;
		this.title = title;
		this.content = content;
	}

	/**
	 * 수정을 요청한 사용자의 ID를 반환합니다.
	 * 
	 * @return 사용자 ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 수정할 게시글 번호를 반환합니다.
	 * 
	 * @return 게시글 번호
	 */
	public int getArticleNumber() {
		return articleNumber;
	}

	/**
	 * 수정할 제목을 반환합니다.
	 * 
	 * @return 제목
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 수정할 내용을 반환합니다.
	 * 
	 * @return 내용
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 입력 데이터의 유효성을 검사하고 발생한 오류를 errors 맵에 저장합니다.
	 * 
	 * @param errors 오류를 저장할 맵 객체
	 */
	public void validate(Map<String, Boolean> errors) {
		if (title == null || title.trim().isEmpty()) {
			// 제목이 null이거나 공백 문자만 포함된 경우

			errors.put("title", Boolean.TRUE);
			// errors 맵에 "title" 키와 TRUE 값을 저장하여 오류를 표시합니다.
		}
	}

}
