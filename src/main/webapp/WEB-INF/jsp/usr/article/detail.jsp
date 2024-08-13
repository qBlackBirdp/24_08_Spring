<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resource/common.css">
<script src="/resource/common.js" defer="defer"></script>
</head>
<body>
	<h1>게시물 상세보기</h1>
	<div class="article-detail">
		<div class="detail-item">
			<span class="label">번호:</span> ${article.id}
		</div>
		<div class="detail-item">
			<span class="label">날짜:</span> ${article.regDate}
		</div>
		<div class="detail-item">
			<span class="label">작성자:</span> ${article.memberId}
		</div>
		<div class="detail-item">
			<span class="label">제목:</span> ${article.title}
		</div>
		<div class="detail-item">
			<span class="label">내용:</span> ${article.body}
		</div>
		<div class="actions">
			<a href="delete" class="btn">게시물 삭제</a> <a href="modify" class="btn">게시물
				수정</a>
		</div>
	</div>

	<div class="navigation">
		<a href="../home/main" class="btn">메인 페이지로</a> <a href="list"
			class="btn">리스트로 돌아가기</a>
	</div>


</body>
</html>