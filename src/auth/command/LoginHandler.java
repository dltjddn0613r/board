package auth.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.LoginFailException;
import auth.service.LoginService;
import auth.service.User;
import mvc.command.CommandHandler;

public class LoginHandler implements CommandHandler {
//로그인에 성공하면 세션의 authUser 속성에 User 객체를 저장한다.
	private static final String FORM_VIEW = "/WEB-INF/view/loginForm.jsp";
	private LoginService loginService = new LoginService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);// GET 요청일 경우 로그인 폼을 보여줍니다.
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res); // POST 요청일 경우 로그인을 처리합니다.
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;// 로그인 폼을 보여주기 위해 FORM_VIEW를 반환합니다.
	}// GET 요청일 때 호출되는 메서드입니다

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {// POST 요청일 때 호출되는
																									// 메서드입니다.
		String id = trim(req.getParameter("id"));
		String password = trim(req.getParameter("password"));

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		// 아이디와 비밀번호가 비어있는지 확인하여 errors 맵에 추가합니다.
		if (id == null || id.isEmpty())
			errors.put("id", Boolean.TRUE);
		if (password == null || password.isEmpty())
			errors.put("password", Boolean.TRUE);

		// 오류가 있으면 로그인 폼으로 돌아갑니다
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		try {// LoginService를 사용하여 로그인을 시도합니다.
			User user = loginService.login(id, password);

			// LoginService를 사용하여 로그인을 시도합니다.
			req.getSession().setAttribute("authUser", user);
			res.sendRedirect(req.getContextPath() + "/index.jsp");
			return null;
		} catch (LoginFailException e) {
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			return FORM_VIEW;
		} // 로그인 실패 시 오류 메시지를 errors 맵에 추가하고 로그인 폼으로 돌아갑니다.
	}

	/**
	 * 문자열의 공백을 제거하는 메서드입니다.
	 *
	 * @param str 공백을 제거할 문자열
	 * @return 공백이 제거된 문자열, 입력이 null인 경우 null을 반환합니다.
	 */
	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}
