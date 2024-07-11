<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>암호 변경</title>
</head>
<body>
<form action="changePwd.do" method="post"> 
<!-- 폼 데이터를 changePwd.do URL로 POST 방식으로 전송 -->
<p>
	현재 암호:<br/><input type="password" name="curPwd"> <!-- 현재 암호 입력 필드 -->
	<c:if test="${errors.curPwd}">현재 암호를 입력하세요.</c:if> 
	<!-- 현재 암호가 비어있을 때 에러 메시지 표시 -->
	
	<c:if test="${errors.badCurPwd}">현재 암호가 일치하지 않습니다.</c:if> 
	<!-- 현재 암호가 일치하지 않을 때 에러 메시지 표시 -->
</p>
<p>
	새 암호:<br/><input type="password" name="newPwd"> <!-- 새 암호 입력 필드 -->
	<c:if test="${errors.newPwd}">새 암호를 입력하세요.</c:if> 
	<!-- 새 암호가 비어있을 때 에러 메시지 표시 -->
</p>
<input type="submit" value="암호 변경"> <!-- 폼 제출 버튼 -->
</form>
</body>
</html>
