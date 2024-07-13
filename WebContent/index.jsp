<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>회원제 게시판 예제</title>
</head>
<body>
<%--  아래의 커스텀 태그사용하여 주석처리함
    주석: 이 페이지는 회원제 게시판 예제를 위한 JSP 페이지입니다ddd.
    authUser 객체를 통해 로그인 여부에 따라 다른 메뉴를 제ddddddd공합니다.   
<%-- 
<c:if test="${! empty authUser}">
	${authUser.name}님, 안녕하세요.
	<a href="logout.do">[로그아웃하기]</a>
	<a href="changePwd.do">[암호변경하기]</a>
</c:if>
<c:if test="${empty authUser}">
	<a href="join.do">[회원가입하기]</a>
	<a href="login.do">[로그인하기]</a>
</c:if>
--%>
<u:isLogin> <!-- 로그인 상태일 때 출력되는 부분 //CT=커스텀 태그 -->
	CT: ${authUser.name}님, 안녕하세요.
	<a href="logout.do">[로그아웃하기]</a>
	<a href="changePwd.do">[암호변경하기]</a>
</u:isLogin>
<u:notLogin> <!-- 비로그인 상태일 때 출력되는 부분 -->
	CT: <a href="join.do">[회원가입하기]</a>
	<a href="login.do">[로그인하기]</a>
</u:notLogin>
<br/>
</body>
</html>