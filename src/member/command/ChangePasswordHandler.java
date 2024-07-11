package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.ChangePasswordService;
import member.service.InvalidPasswordException;
import member.service.MemberNotFoundException;
import mvc.command.CommandHandler;

public class ChangePasswordHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/changePwdForm.jsp";
	// 비밀번호 변경 폼 경로

	private ChangePasswordService changePwdSvc = new ChangePasswordService();
	// 비밀번호 변경 서비스 객체

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);// HTTP 메서드가 GET이면 폼을 보여줌

		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);// HTTP 메서드가 POST이면 폼 제출을 처리

		} else {
			res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;// 그 외의 메서드는 허용하지 않음
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;// 비밀번호 변경 폼을 보여주는 메서드
	}

	// 폼 제출을 처리하는 메서드
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 현재 로그인한 사용자의 정보를 세션에서 가져옴
		User user = (User) req.getSession().getAttribute("authUser");

		// 에러 메시지를 저장할 Map 객체 생성
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		// 폼에서 전송된 현재 비밀번호와 새 비밀번호를 가져옴
		String curPwd = req.getParameter("curPwd");
		String newPwd = req.getParameter("newPwd");

		// 현재 비밀번호와 새 비밀번호가 입력되지 않았을 경우 에러 메시지 추가
		if (curPwd == null || curPwd.isEmpty()) {
			errors.put("curPwd", Boolean.TRUE);
		}
		if (newPwd == null || newPwd.isEmpty()) {
			errors.put("newPwd", Boolean.TRUE);
		}

		// 에러가 있을 경우 비밀번호 변경 폼을 다시 보여줌
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		try {
			// 비밀번호 변경 서비스 호출
			changePwdSvc.changePassword(user.getId(), curPwd, newPwd);
			// 성공 시 성공 페이지로 이동
			return "/WEB-INF/view/changePwdSuccess.jsp";
		} catch (InvalidPasswordException e) {
			// 현재 비밀번호가 틀린 경우 에러 메시지 추가 후 폼을 다시 보여줌
			errors.put("badCurPwd", Boolean.TRUE);
			return FORM_VIEW;
		} catch (MemberNotFoundException e) {
			// 사용자를 찾을 수 없는 경우 400 에러를 반환
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}
}