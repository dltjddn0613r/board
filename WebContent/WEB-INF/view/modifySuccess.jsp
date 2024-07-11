<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>게시글 수정</title>
</head>
<body>

게시글을 수정했습니다.
<br>
${ctxPath = pageContext.request.contextPath ; ''}
<a href="${ctxPath}/article/list.do">[게시글목록보기]</a>
<!-- 사용자가 게시글 목록 페이지로 이동할 수 있는 링크를 제공합니다. -->

<a href="${ctxPath}/article/read.do?no=${modReq.articleNumber}">[게시글내용보기]</a>
<!-- 사용자가 방금 수정한 게시글의 내용을 볼 수 있는 링크를 제공합니다. 게시글 번호는 modReq 객체에서 가져옵니다. -->
</body>
</html>