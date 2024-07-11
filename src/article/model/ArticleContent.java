package article.model;

public class ArticleContent {

	// 필드 선언: 게시글 번호, 내용
	private Integer number;
	private String content;

	// 생성자: 필드 초기화
	public ArticleContent(Integer number, String content) {
		this.number = number; // 게시글 번호 초기화
		this.content = content; // 내용 초기화
	}

	// 게시글 번호 반환 메서드
	public Integer getNumber() {
		return number;
	}

	// 내용 반환 메서드
	public String getContent() {
		return content;
	}
}
