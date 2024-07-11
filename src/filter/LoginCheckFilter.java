package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {

	/**
	 * * 필터를 이용하여 로그인 여부를 검사합니다. 필터에서 로그인 여부를 검사하면, 나머지 코드를 수정할 필요 없이 로그인 여부 검사 로직을
	 * 수행할 수 있다. 단지 필터를 알맞게 구현하고 로그인 여부를 검사해야 하는 경로에 필터를 매핑해주기만 하면된다.
	 * 
	 * @param req   ServletRequest 객체로서 클라이언트 요청을 나타냅니다.
	 * @param res   ServletResponse 객체로서 클라이언트 응답을 나타냅니다.
	 * @param chain FilterChain 객체로서 다음 필터로 요청을 전달하거나 최종적으로 서블릿을 실행합니다.
	 * @throws IOException      입출력 오류가 발생할 경우
	 * @throws ServletException 서블릿에서 발생한 예외가 전달될 경우
	 */

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// HttpServletRequest로 형변환하여 세션을 사용하기 위해 HttpSession을 가져옵니다
		HttpServletRequest request = (HttpServletRequest) req;

		HttpSession session = request.getSession(false);
		// false를 주면 세션이 존재하지 않아도 새로 생성하지 않고 null을 반환합니다.

		// 세션이 없거나 인증된 사용자(authUser) 정보가 없으면 로그인 페이지로 리다이렉트합니다.
		if (session == null || session.getAttribute("authUser") == null) {
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect(request.getContextPath() + "/login.do");
		} else {
			chain.doFilter(req, res);
		} // 세션이 존재하고 인증된 사용자일 경우 요청을 다음 필터로 전달하거나 최종적으로 서블릿을 실행합니다.
	}

	/**
	 * 필터 초기화 메소드입니다. 초기화 과정이 필요 없으므로 비워둡니다.
	 * 
	 * @param config FilterConfig 객체로서 필터의 설정 정보를 나타냅니다.
	 * @throws ServletException 서블릿에서 발생한 예외가 전달될 경우
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
	} // 초기화 과정이 필요 없으므로 비워둡니다.

	@Override
	public void destroy() {
	}
	/**
	 * 필터 해제 메소드입니다. 해제 과정이 필요 없으므로 비워둡니다.
	 */

}
