<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>가입 완료</title>
</head>
<body>
${param.name}님, 회원 가입에 성공했습니다.
<!-- param.name: HTTP 요청 파라미터 중 'name'에 해당하는 값으로, 사용자가 입력한 이름을 출력 -->
<a href="logout.do">[로그아웃하기]</a><!-- 직접추가함 -->
<br/>
</body>
</html>