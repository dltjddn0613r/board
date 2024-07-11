package article.model;

import java.util.Date;

public class Article {

	// 필드 선언: 게시글 번호, 작성자, 제목, 등록일, 수정일, 조회수
	private Integer number;
	private Writer writer;
	private String title;
	private Date regDate;
	private Date modifiedDate;
	private int readCount;

	// 생성자: 모든 필드를 초기화
	public Article(Integer number, Writer writer, String title, Date regDate, Date modifiedDate, int readCount) {
		this.number = number; // 게시글 번호 초기화
		this.writer = writer; // 작성자 초기화
		this.title = title; // 제목 초기화
		this.regDate = regDate; // 등록일 초기화
		this.modifiedDate = modifiedDate; // 수정일 초기화
		this.readCount = readCount; // 조회수 초기화
	}

	// 게시글 번호 반환 메서드
	public Integer getNumber() {
		return number;
	}

	// 작성자 반환 메서드
	public Writer getWriter() {
		return writer;
	}

	// 제목 반환 메서드
	public String getTitle() {
		return title;
	}

	// 등록일 반환 메서드
	public Date getRegDate() {
		return regDate;
	}

	// 수정일 반환 메서드
	public Date getModifiedDate() {
		return modifiedDate;
	}

	// 조회수 반환 메서드
	public int getReadCount() {
		return readCount;
	}

}
