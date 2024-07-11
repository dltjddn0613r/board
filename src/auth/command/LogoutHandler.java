package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;

public class LogoutHandler implements CommandHandler {
//LogoutHandler는 세션이 존재하면 세션을 종료한다
//세션을 종료하면 authUser 속성도 함께 삭제되므로 로그아웃처리된다.
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {// 현재 요청의 세션을 가져옴.세션이 없으면
																								// 새로 생성하지 않고 null을
																								// 반환합니다.
		HttpSession session = req.getSession(false);
		if (session != null) {// 세션이 존재하면
			session.invalidate();
		} // 세션을 무효화합니다. 이로써 세션에 저장된 모든 속성들이 제거됩니다.
		res.sendRedirect(req.getContextPath() + "/index.jsp"); // 로그아웃 후 메인 페이지로 이동합니다.
		return null;
	}// 커맨드 핸들러에서 반환하는 값은 다음 처리할 뷰 페이지의 경로를 나타냅니다.
		// 여기서는 메인 페이지로 이동하므로 null을 반환합니다. index.jsp

}
