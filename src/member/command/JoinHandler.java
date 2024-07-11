package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.DuplicateIdException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;

/**
 * 회원 가입 처리를 담당하는 핸들러 클래스입니다.
 */
public class JoinHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/joinForm.jsp";// 회원 가입 폼 뷰 경로
	private JoinService joinService = new JoinService();// 회원 가입 서비스 객체 생성

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) {
		if (req.getMethod().equalsIgnoreCase("GET")) {// equalsIgnoreCase문자열을 비교할 때 대소문자를 무시하고 비교하는 메서드
			return processForm(req, res);// GET 요청 처리
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);// POST 요청 처리
		} else {// 허용되지 않는 HTTP 메서드 처리
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	/**
	 * GET 요청 처리 메서드
	 * 
	 * @param req HTTP 요청 객체
	 * @param res HTTP 응답 객체
	 * @return 회원 가입 폼 뷰 경로
	 */
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;// 회원 가입 폼 뷰로 이동
	}

	/**
	 * POST 요청 처리 메서드
	 * 
	 * @param req HTTP 요청 객체
	 * @param res HTTP 응답 객체
	 * @return 회원 가입 성공 시 성공 페이지 뷰 경로, 중복 아이디 예외 발생 시 회원 가입 폼 뷰 경로
	 */
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		JoinRequest joinReq = new JoinRequest();// 회원 가입 요청 객체 생성
		joinReq.setId(req.getParameter("id"));// 요청 파라미터에서 아이디 설정
		joinReq.setName(req.getParameter("name")); // 요청 파라미터에서 이름 설정
		joinReq.setPassword(req.getParameter("password")); // 요청 파라미터에서 비밀번호 설정
		joinReq.setConfirmPassword(req.getParameter("confirmPassword")); // 요청 파라미터에서 확인 비밀번호 설정

		Map<String, Boolean> errors = new HashMap<>();// 오류 정보를 담을 맵 생성
		req.setAttribute("errors", errors);// 오류 정보를 요청 속성에 설정

		joinReq.validate(errors);// 회원 가입 요청의 유효성 검사 수행

		if (!errors.isEmpty()) { // 오류가 있을 경우
			return FORM_VIEW;// 회원 가입 폼 뷰로 이동
		}

		try {
			joinService.join(joinReq);// 회원 가입 서비스를 통해 회원 가입 처리
			return "/WEB-INF/view/joinSuccess.jsp"; // 회원 가입 성공 페이지 뷰로 이동
		} catch (DuplicateIdException e) {// 중복된 아이디 예외 처리
			errors.put("duplicateId", Boolean.TRUE); // 중복 아이디 오류를 오류 맵에 추가
			return FORM_VIEW;// 회원 가입 폼 뷰로 이동
		}
	}

}
