package article.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.PermissionDeniedException;
import article.service.ReadArticleService;
import auth.service.User;
import mvc.command.CommandHandler;

public class ModifyArticleHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/modifyForm.jsp"; // 수정 폼의 JSP 경로

	private ReadArticleService readService = new ReadArticleService(); // 게시글 조회 서비스 객체
	private ModifyArticleService modifyService = new ModifyArticleService(); // 게시글 수정 서비스 객체

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res); // GET 요청 처리 메서드 호출
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res); // POST 요청 처리 메서드 호출
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); // 허용되지 않는 HTTP 메서드일 경우 405 오류 반환
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			String noVal = req.getParameter("no"); // 요청 파라미터에서 게시글 번호 조회
			int no = Integer.parseInt(noVal); // 게시글 번호 변환
			ArticleData articleData = readService.getArticle(no, false); // 게시글 조회 서비스로 게시글 데이터 조회
			User authUser = (User) req.getSession().getAttribute("authUser"); // 세션에서 인증된 사용자 정보 조회

			// 수정 권한 확인
			if (!canModify(authUser, articleData)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN); // 권한 없음 오류 반환
				return null;
			}

			// 수정 요청 객체 생성 및 속성 설정
			ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, articleData.getArticle().getTitle(),
					articleData.getContent());
			req.setAttribute("modReq", modReq); // 수정 요청 객체를 request에 저장
			return FORM_VIEW; // 수정 폼으로 이동
		} catch (NumberFormatException e) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST); // 잘못된 요청 파라미터 형식 오류 반환
			return null;
		} catch (ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND); // 게시글을 찾을 수 없음 오류 반환
			return null;
		} catch (Exception e) {
			e.printStackTrace(); // 예외 로그 출력
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 서버 내부 오류 반환
			return null;
		}
	}

	/**
	 * 수정 권한을 확인합니다.
	 * 
	 * @param authUser    현재 인증된 사용자 객체
	 * @param articleData 조회된 게시글 데이터 객체
	 * @return 수정 권한 여부 (true: 수정 가능, false: 수정 불가능)
	 */
	private boolean canModify(User authUser, ArticleData articleData) {
		String writerId = articleData.getArticle().getWriter().getId(); // 게시글 작성자의 ID 조회
		return authUser.getId().equals(writerId); // 현재 사용자의 ID와 게시글 작성자의 ID 비교
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		User authUser = (User) req.getSession().getAttribute("authUser"); // 세션에서 인증된 사용자 정보 조회
		String noVal = req.getParameter("no"); // 요청 파라미터에서 게시글 번호 조회
		int no = Integer.parseInt(noVal); // 게시글 번호 변환

		// 수정 요청 객체 생성 및 속성 설정
		ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, req.getParameter("title"),
				req.getParameter("content"));
		req.setAttribute("modReq", modReq); // 수정 요청 객체를 request에 저장

		Map<String, Boolean> errors = new HashMap<>(); // 유효성 검사 오류를 담을 Map 생성
		req.setAttribute("errors", errors); // 오류 정보를 request에 저장
		modReq.validate(errors); // 수정 요청의 유효성 검사 수행

		if (!errors.isEmpty()) { // 유효성 검사에서 오류가 발생한 경우
			return FORM_VIEW; // 수정 폼으로 이동
		}

		try {
			modifyService.modify(modReq); // 게시글 수정 서비스 호출
			return "/WEB-INF/view/modifySuccess.jsp"; // 수정 성공 페이지로 이동
		} catch (ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND); // 게시글을 찾을 수 없음 오류 반환
			return null;
		} catch (PermissionDeniedException e) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN); // 권한 없음 오류 반환
			return null;
		} catch (Exception e) {
			e.printStackTrace(); // 예외 로그 출력
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 서버 내부 오류 반환
			return null;
		}
	}
}
