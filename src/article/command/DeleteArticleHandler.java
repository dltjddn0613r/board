package article.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.DeleteArticleService;
import auth.service.DeleteRequest;
import member.service.DuplicateIdException;
import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/deleteArticleForm.jsp";
	private DeleteArticleService deleteService = new DeleteArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws IOException {

		String noVal = req.getParameter("no");
		int no = Integer.parseInt(noVal);

		DeleteRequest deleteReq = new DeleteRequest();
		deleteReq.setArticleNumber(no);

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		if (!errors.isEmpty()) {
			System.out.println(errors);
			return "/WEB-INF/view/deleteArticleFail.jsp";
		}

		try {
			deleteService.delete(deleteReq);
			return "/WEB-INF/view/deleteArticleSuccess.jsp";
		} catch (DuplicateIdException e) {
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
	}

}