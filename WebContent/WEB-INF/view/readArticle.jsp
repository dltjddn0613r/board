<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>게시글 읽기</title>
</head>
<body>
<!-- 테이블을 생성하고, 경계선이 있는 테이블을 나타내며 전체 너비를 100%로 설정합니다. -->
<table border="1" width="100%">
<tr><!-- 테이블 행을 나타내며 각 게시글의 정보를 행으로 표시합니다. -->
	<td>번호</td><!--<td>: 테이블 셀을 나타내며 각 정보의 제목과 내용을 표시합니다.  -->
	<td>${articleData.article.number}</td><!-- 게시글 번호를 출력합니다. -->
</tr>
<tr>
	<td>작성자</td>
	<td>${articleData.article.writer.name}</td><!-- 게시글 작성자의 이름 출력합니다. -->
</tr>
<tr>
	<td>제목</td><!-- 게시글 제목을 출력하며 HTML 특수 문자를 안전하게 출력합니다. -->
	<td><c:out value='${articleData.article.title}' /></td>
</tr>
<tr>
	<td>내용</td><!--  사용자 정의 태그를 사용하여 게시글 내용을 출력합니다. 이 태그는 내용을 HTML로 안전하게 출력하고 줄 바꿈을 처리합니다. -->
	<td><u:pre value='${articleData.content}'/></td>
</tr>
<tr>
	<td colspan="2">
	<!-- 현재 페이지 번호를 설정합니다. 만약 pageNo 파라미터가 비어있으면 기본값으로 1을 사용합니다. -->
		<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo}" />
		
		<!-- 게시글 목록으로 돌아가는 링크를 생성하며, pageNo를 파라미터로 전달합니다. -->
		<a href="list.do?pageNo=${pageNo}">[목록]</a>
		
		<!--현재 사용자가 게시글의 작성자와 같은 경우에만 수정과 삭제 링크를 출력합니다.  -->
		<c:if test="${authUser.id == articleData.article.writer.id}">
		
		<!-- 게시글 수정 링크를 생성하며, 게시글 번호를 파라미터로 전달합니다. -->
		<a href="modify.do?no=${articleData.article.number}">[게시글수정]</a>
		
		<!--게시글 삭제 링크를 생성하며, 게시글 번호를 파라미터로 전달합니다.  -->
		<a href="delete.do?no=${articleData.article.number}"><button>[게시글삭제]</button></a>
		</c:if>
	</td>
</tr>
</table>

</body>
</html>