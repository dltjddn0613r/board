<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>게시글 쓰기</title>
</head>
<body>
<form action="write.do" method="post">
<p>
	제목:<br/><input type="text" name="title" value="${param.title}">
	<c:if test="${errors.title}">제목을 입력하세요.</c:if> <!-- 제목 입력 오류 시 메시지 -->
	
</p>
<p>
	내용:<br/>
	<textarea name="content" rows="5" cols="30">${param.content}</textarea>
	<!-- 내용 입력란입니다. ${param.content}는 이전에 입력한 내용을 유지하기 위해 사용됩니다. -->
</p>
<input type="submit" value="새 글 등록">
</form>
</body>
</html>