<%@ tag body-content="scriptless" pageEncoding="utf-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<% // HttpSession 객체를 가져옵니다. 세션이 존재하지 않으면 null을 반환합니다.
	HttpSession httpSession = request.getSession(false);

//세션이 존재하지 않거나, 세션에 "authUser" 속성이 존재하지 않으면
	if (httpSession == null || httpSession.getAttribute("authUser") == null) {
%><!-- 조건이 참일 때 jsp:doBody 태그를 사용하여 태그의 본문 내용을 실행합니다. -->
<jsp:doBody />
<%
	}// if 블록 종료
%>