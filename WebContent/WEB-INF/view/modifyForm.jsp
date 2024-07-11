<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>게시글 수정</title>
</head>
<body>
    <form action="modify.do" method="post">
     <!-- 폼을 생성하여 POST 메서드를 사용하여 "modify.do"로 데이터를 전송합니다. -->
     
        <input type="hidden" name="no" value="${modReq.articleNumber}">
          <!-- 게시글 번호를 히든 필드로 설정하여 폼에 포함시킵니다. -->
        <p>
            번호:<br/>${modReq.articleNumber}<!-- 게시글 번호를 출력합니다. -->
        </p>
        
        <p>
            제목:<br/>
            <input type="text" name="title" value="${modReq.title}">
             <!-- 제목을 입력할 수 있는 텍스트 필드를 생성하고, 현재 제목을 기본값으로 설정합니다. -->
             
            <c:if test="${errors.title}">제목을 입력하세요.</c:if>
            <!-- 제목 필드가 비어있을 경우 에러 메시지를 출력합니다. -->
        </p>
        
        <p>
            내용:<br/>
            <textarea name="content" rows="5" cols="30">${modReq.content}</textarea>
             <!-- 내용을 입력할 수 있는 텍스트 영역을 생성하고, 현재 내용을 기본값으로 설정합니다. -->
        </p>
        
        <input type="submit" value="글 수정">
         <!-- 폼 데이터를 제출하기 위한 제출 버튼을 생성합니다. -->
    </form>
</body>
</html>
