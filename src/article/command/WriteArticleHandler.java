package article.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Writer;
import article.service.WriteArticleService;
import article.service.WriteRequest;
import auth.service.User;
import mvc.command.CommandHandler;

/**
 * 새 게시글 작성을 처리하는 핸들러
 */
public class WriteArticleHandler implements CommandHandler {
	// 게시글 작성 폼 경로
	private static final String FORM_VIEW = "/WEB-INF/view/newArticleForm.jsp";

	// WriteArticleService 객체
	private WriteArticleService writeService = new WriteArticleService();

	/**
	 * HTTP 요청 처리 메서드
	 * 
	 * @param req HttpServletRequest 객체
	 * @param res HttpServletResponse 객체
	 * @return 뷰 경로
	 */
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);// GET 요청일 경우 폼 처리 메서드 호출
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);// POST 요청일 경우 제출 처리 메서드 호출
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;// 허용되지 않은 HTTP 메서드일 경우 에러 응답
		}
	}

	/**
	 * GET 요청 처리 메서드: 게시글 작성 폼을 반환
	 * 
	 * @param req HttpServletRequest 객체
	 * @param res HttpServletResponse 객체
	 * @return 게시글 작성 폼 경로
	 */
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	/**
	 * POST 요청 처리 메서드: 게시글 작성 제출 처리
	 * 
	 * @param req HttpServletRequest 객체
	 * @param res HttpServletResponse 객체
	 * @return 처리 결과 뷰 경로
	 */
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Boolean> errors = new HashMap<>();// 오류 정보를 담는 Map 객체 생성
		req.setAttribute("errors", errors);// 오류 정보를 request에 저장

		// 세션에서 로그인 사용자 정보 가져오기
		User user = (User) req.getSession(false).getAttribute("authUser");

		// WriteRequest 객체 생성
		WriteRequest writeReq = createWriteRequest(user, req);
		writeReq.validate(errors);// 입력 유효성 검사

		if (!errors.isEmpty()) { // 오류가 있을 경우
			return FORM_VIEW; // 폼 뷰로 이동
		}

		// 게시글 작성 서비스 호출하여 게시글 저장
		int newArticleNo = writeService.write(writeReq);

		// 새로 생성된 게시글 번호를 request에 저장
		req.setAttribute("newArticleNo", newArticleNo);

		return "/WEB-INF/view/newArticleSuccess.jsp";// 작성 성공 뷰로 이동
	}

	/**
	 * WriteRequest 객체 생성 메서드
	 * 
	 * @param user 현재 로그인 사용자 정보를 담고 있는 User 객체
	 * @param req  HttpServletRequest 객체
	 * @return 생성된 WriteRequest 객체
	 */
	private WriteRequest createWriteRequest(User user, HttpServletRequest req) {
		return new WriteRequest(new Writer(user.getId(), user.getName()), // 작성자 정보 설정
				req.getParameter("title"), // 제목 설정
				req.getParameter("content")); // 내용 설정
	}
}
