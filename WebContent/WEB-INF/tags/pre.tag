<%@ tag body-content="empty" pageEncoding="utf-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="value" type="java.lang.String" required="true"%>
<%
	// 전달받은 value 속성 값에서 특정 문자를 HTML 엔티티로 치환하여 출력하는 커스텀 태그입니다.
	// HTML 엔티티 변환: &, <, 공백, 개행 문자 처리

	value = value.replace("&", "&amp;"); // &를 &amp;으로 치환
	value = value.replace("<", "&lt;"); // <를 &lt;로 치환
	value = value.replace(" ", "&nbsp;"); // 공백을 &nbsp;로 치환
	value = value.replace("\n", "\n<br>"); // 개행 문자를 <br>로 변환하여 줄 바꿈 처리
%>
<%= value %>
