<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>게시글 삭제</title>
</head>
<body>
<form id="delete_form" action="delete.do" method="post">
<p>
	<input type="hidden" name="no" value="${param.no}"><!-- 게시글 번호: -->
</p>

</form>

<script type="text/javascript"> 
this.document.getElementById("delete_form").submit(); 
</script> 

</body>
</html>