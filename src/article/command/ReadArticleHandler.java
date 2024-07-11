package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleContentNotFoundException;
import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ReadArticleService;
import mvc.command.CommandHandler;

/**
 * 특정 게시글을 조회하는 핸들러 클래스입니다.
 */
public class ReadArticleHandler implements CommandHandler {

	private ReadArticleService readService = new ReadArticleService();

	/**
	 * GET 또는 POST 요청을 처리하는 메서드입니다.
	 * 
	 * @param req HTTP 요청 객체
	 * @param res HTTP 응답 객체
	 * @return 응답에 사용할 뷰 경로
	 * @throws Exception 예외 발생 시 전파됩니다.
	 */
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String noVal = req.getParameter("no");
		int articleNum = Integer.parseInt(noVal);

		try {
			// ReadArticleService를 사용하여 articleNum에 해당하는 게시글 정보를 조회합니다.
			ArticleData articleData = readService.getArticle(articleNum, true);

			// 조회된 게시글 정보를 request 객체에 저장합니다.
			req.setAttribute("articleData", articleData);

			// 응답에 사용할 뷰 경로를 반환합니다.
			return "/WEB-INF/view/readArticle.jsp";

		} catch (ArticleNotFoundException | ArticleContentNotFoundException e) {
			// ArticleNotFoundException 또는 ArticleContentNotFoundException 예외 처리
			req.getServletContext().log("no article", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

}
