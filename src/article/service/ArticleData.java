package article.service;

import article.model.Article;
import article.model.ArticleContent;

/**
 * 게시글과 게시글 내용을 담는 클래스입니다.
 */
public class ArticleData {

	private Article article;
	private ArticleContent content;

	/**
	 * ArticleData 객체를 생성합니다.
	 * 
	 * @param article 게시글 정보를 담고 있는 Article 객체
	 * @param content 게시글 내용을 담고 있는 ArticleContent 객체
	 */
	public ArticleData(Article article, ArticleContent content) {
		this.article = article;
		this.content = content;
	}

	/**
	 * 이 메서드는 게시글 정보를 반환합니다.
	 * 
	 * @return Article 객체
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * 이 메서드는 게시글 내용을 반환합니다.
	 * 
	 * @return 게시글 내용
	 */
	public String getContent() {
		return content.getContent();
	}

}
