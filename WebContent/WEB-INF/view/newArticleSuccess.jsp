<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>게시글 등록</title>
</head>
<body>

게시글을 등록했습니다.
<br>
${ctxPath = pageContext.request.contextPath ; ''}

<!-- 게시글 목록 보기 링크 -->
<a href="${ctxPath}/article/list.do">[게시글목록보기]</a>

<!-- 새로 등록한 게시글의 내용 보기 링크 -->
<a href="${ctxPath}/article/read.do?no=${newArticleNo}">[게시글내용보기]</a>
</body>
</html>