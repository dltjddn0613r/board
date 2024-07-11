package article.service;

import java.util.Map;

import article.model.Writer;

/**
 * 게시글 작성 요청을 나타내는 클래스
 */
public class WriteRequest {

	private Writer writer; // 작성자 정보
	private String title; // 제목
	private String content; // 내용

	/**
	 * WriteRequest 객체 생성자
	 * 
	 * @param writer  작성자 정보를 담고 있는 Writer 객체
	 * @param title   게시글 제목
	 * @param content 게시글 내용
	 */
	public WriteRequest(Writer writer, String title, String content) {
		this.writer = writer;
		this.title = title;
		this.content = content;
	}

	/**
	 * 작성자 정보를 반환하는 메서드
	 * 
	 * @return 작성자 정보를 담고 있는 Writer 객체
	 */
	public Writer getWriter() {
		return writer;
	}

	/**
	 * 게시글 제목을 반환하는 메서드
	 * 
	 * @return 게시글 제목
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 게시글 내용을 반환하는 메서드
	 * 
	 * @return 게시글 내용
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 유효성 검사 메서드
	 * 
	 * @param errors 유효성 검사 에러를 저장할 Map 객체
	 */
	public void validate(Map<String, Boolean> errors) {
		if (title == null || title.trim().isEmpty()) {
			errors.put("title", Boolean.TRUE);
		}
	}
}
