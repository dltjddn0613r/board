<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>가입</title>
</head>
<body>
<form action="join.do" method="post">
<p>
	아이디:<br/><input type="text" name="id" value="${param.id}">
	<!-- 아이디 입력 필드. ${param.id}는 이전에 입력한 아이디 값이 있으면 그 값을 표시함 -->
	<c:if test="${errors.id}">ID를 입력하세요.</c:if>
	<!-- errors 맵에서 id 에러가 true인 경우에 메시지 표시 -->
	<c:if test="${errors.duplicateId}">이미 사용중인 아이디입니다.</c:if>
	<!-- errors 맵에서 duplicateId 에러가 true인 경우에 중복 아이디 메시지 표시 -->
</p>
<p>
	이름:<br/>
	<input type="text" name="name" value="${param.name}">
	<!-- 이름 입력 필드. ${param.name}은 이전에 입력한 이름 값이 있으면 그 값을 표시함 -->
	<c:if test="${errors.name}">이름을 입력하세요.</c:if>
	<!-- errors 맵에서 name 에러가 true인 경우에 메시지 표시 -->
</p>
<p>
	암호:<br/>
	<input type="password" name="password"><!-- 암호 입력 필드 -->
	<c:if test="${errors.password}">암호를 입력하세요.</c:if>
	<!-- errors 맵에서 password 에러가 true인 경우에 메시지 표시 -->
</p>
<p>
	확인:<br/>
	<input type="password" name="confirmPassword"><!-- 확인 암호 입력 필드 -->
	<c:if test="${errors.confirmPassword}">확인을 입력하세요.</c:if>
	<!-- errors 맵에서 confirmPassword 에러가 true인 경우에 메시지 표시 -->
	<c:if test="${errors.notMatch}">암호와 확인이 일치하지 않습니다.</c:if>
	<!-- errors 맵에서 notMatch 에러가 true인 경우에 일치하지 않음 메시지 표시 -->
</p>
<input type="submit" value="가입">
<!-- 가입 버튼 -->
</form>
</body>
</html>