<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${board.code } 목록"></c:set>
<%@ include file="../common/head.jspf"%>

<hr />

<table class="article-table">
	<thead>
		<tr>
			<th>ID</th>
			<th>Registration Date</th>
			<th>Title</th>
			<th>Writer</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="article" items="${articles}">
			<tr>
				<td>${article.id}</td>
				<td>${article.regDate.substring(0, 10)}</td>
				<td><a href="detail?id=${article.id}">${article.title}</a></td>
				<td>${article.extra__writer}</td>
			</tr>
		</c:forEach>
		<c:if test="${empty articles}">
			<tr>
				<td colspan="4" style="text-align: center;">게시글이 없습니다</td>
			</tr>
		</c:if>
	</tbody>
</table>

<c:if test="${rq.isLogined()}">
	<div
		style="text-align: right; margin-bottom: 10px; margin-right: 20px;">
		<a href="write" class="btn">게시물 작성하기</a>
	</div>
</c:if>



</body>
</html>