<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resource/common.css">
<script src="/resource/common.js" defer="defer"></script>
<title>Article List</title>
</head>
<body>
	<h1>게시물 목록</h1>
	
	<hr/>
	
	<table class="article-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Registration Date</th>
                <th>Title</th>
                <th>Member ID</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="article" items="${articles}">
                <tr>
                    <td>${article.id}</td>
                    <td>${article.regDate.substring(0, 10)}</td>
                    <td><a href="detail?id=${article.id}">${article.title}</a></td>
                    <td>${article.memberId}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	
	
	
</body>
</html>