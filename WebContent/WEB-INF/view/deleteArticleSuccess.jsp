<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="pageNo" value="${empty modReq.pageNumber ? '1' : modReq.pageNumber}" />
<meta http-equiv="refresh" content="0; url=/board/article/list.do?pageNo=${pageNo}"></meta>
<title>게시글 삭제</title>
<script>
	alert('게시글이 삭제에 성공하였습니다.');
</script>
</head>
<body>

</body>
</html>