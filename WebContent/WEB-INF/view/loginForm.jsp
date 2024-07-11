<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>로그인</title>
</head>
<body>
<form action="login.do" method="post">
<!-- 아이디와 암호가 일치하지 않을 경우에 출력될 메시지 -->
<c:if test="${errors.idOrPwNotMatch}">
아이디와 암호가 일치하지 않습니다.
</c:if>
<p>
	아이디:<br/><input type="text" name="id" value="${param.id}">
	<!-- 아이디 입력이 없을 경우에 출력될 메시지 -->
	<c:if test="${errors.id}">ID를 입력하세요.</c:if>
</p>
<p>
	암호:<br/><input type="password" name="password">
	<!-- 비밀번호 입력이 없을 경우에 출력될 메시지 -->
	<c:if test="${errors.password}">암호를 입력하세요.</c:if>
</p>
<input type="submit" value="로그인">  <!-- 로그인 버튼 -->
</form>
</body>
</html>