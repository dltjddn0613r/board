package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticlePage;
import article.service.ListArticleService;
import mvc.command.CommandHandler;

public class ListArticleHandler implements CommandHandler {

	private ListArticleService listService = new ListArticleService();

	/**
	 * 게시글 목록을 처리하는 핸들러 메서드
	 * 
	 * @param req HttpServletRequest 객체
	 * @param res HttpServletResponse 객체
	 * @return 뷰 페이지 경로
	 * @throws Exception 예외 발생 시 처리
	 */
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 요청 파라미터에서 페이지 번호 가져오기
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;// 기본 페이지 번호는 1로 설정
		if (pageNoVal != null) {// 페이지 번호가 null이 아니면 정수로 변환하여 pageNo에 저장
			pageNo = Integer.parseInt(pageNoVal);
		}
		// ListArticleService를 사용하여 해당 페이지의 ArticlePage 객체 가져오기
		ArticlePage articlePage = listService.getArticlePage(pageNo);

		// 가져온 ArticlePage 객체를 request 속성에 저장
		req.setAttribute("articlePage", articlePage);

		// 뷰 페이지 경로 반환
		return "/WEB-INF/view/listArticle.jsp";
	}

}
