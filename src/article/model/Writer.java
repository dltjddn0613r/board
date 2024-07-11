package article.model;

public class Writer {// 게시글 작성자 정보를 담는 클래스
	// 작성자의 아이디와 이름값을 저장한다

	private String id;
	private String name;

	public Writer(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
