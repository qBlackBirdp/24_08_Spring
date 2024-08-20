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


<div class="pagination">
	<c:if test="${currentPage > 1}">
		<a href="?boardId=${boardId}&page=${currentPage - 1}" class="btn">Previous</a>
	</c:if>

	<c:choose>
		<c:when test="${currentPage == 1}">
			<span class="current">1</span>
		</c:when>
		<c:otherwise>
			<a href="?boardId=${boardId}&page=1" class="btn">1</a>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${currentPage > 3}">
			<span class="dots" onclick="showPageInput(this)">...</span>
			<div class="page-input hidden">
				<input type="number" class="pageInputField" min="1"
					max="${totalPages}" placeholder="Page number">
				<button onclick="goToPage(this, '${boardId}')">Go</button>
			</div>
		</c:when>
	</c:choose>

	<c:forEach var="i" begin="${currentPage > 3 ? currentPage - 2 : 2}"
		end="${currentPage + 2 > totalPages ? totalPages - 1 : currentPage + 2}">
		<c:choose>
			<c:when test="${i == currentPage}">
				<span class="current">${i}</span>
			</c:when>
			<c:otherwise>
				<a href="?boardId=${boardId}&page=${i}" class="btn">${i}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:choose>
		<c:when test="${currentPage < totalPages - 2}">
			<span class="dots" onclick="showPageInput(this)">...</span>
			<div class="page-input hidden">
				<input type="number" class="pageInputField" min="1"
					max="${totalPages}" placeholder="Page number">
				<button onclick="goToPage(this, '${boardId}')">Go</button>
			</div>
		</c:when>
	</c:choose>

	<c:choose>
		<c:when test="${currentPage == totalPages}">
			<span class="current">${totalPages}</span>
		</c:when>
		<c:otherwise>
			<a href="?boardId=${boardId}&page=${totalPages}" class="btn">${totalPages}</a>
		</c:otherwise>
	</c:choose>

	<c:if test="${currentPage < totalPages}">
		<a href="?boardId=${boardId}&page=${currentPage + 1}" class="btn">Next</a>
	</c:if>
</div>

<div class="search-bar">
    <form action="/usr/article/list" method="GET">
        <div class="search-container">
            <!-- 첫 번째 칸: 제목, 내용, 작성자 선택 -->
            <select name="searchField" class="search-field-select">
                <option value="title">제목</option>
                <option value="body">내용</option>
                <option value="extra__writer">작성자</option>
            </select>

            <!-- 두 번째 칸: 사용자 입력 -->
            <input type="text" name="searchKeyword" class="search-input" placeholder="검색어를 입력하세요">

            <!-- 검색 버튼 -->
            <button type="submit" class="search-button">검색</button>
        </div>
        
        <!-- Hidden field for boardId to keep the context of the board -->
        <input type="hidden" name="boardId" value="${boardId}">
        
        <!-- Hidden field for the current page to keep pagination context -->
        <input type="hidden" name="page" value="${currentPage}">
    </form>
</div>

<script>
function showPageInput(dotsElement) {
    var inputDiv = dotsElement.nextElementSibling;
    inputDiv.classList.toggle("hidden");

    var rect = dotsElement.getBoundingClientRect();
    inputDiv.style.top = (rect.bottom + window.scrollY) + "px";
    inputDiv.style.left = (rect.left + window.scrollX - inputDiv.offsetWidth / 2 + dotsElement.offsetWidth / 2) + "px";
}

function goToPage(buttonElement, boardId) {
    var pageInputField = buttonElement.previousElementSibling;
    var pageNumber = parseInt(pageInputField.value, 10);

    // 페이지 번호가 유효한 범위 내에 있는지 확인합니다.
    if (!isNaN(pageNumber) && pageNumber >= 1 && pageNumber <= ${totalPages}) {
        // 유효한 페이지 번호를 URL에 전달하여 이동합니다.
        window.location.href = `?boardId=${boardId}&page=${pageNumber}`;
    } else {
        alert("Please enter a valid page number");
    }
}

// 페이지 입력 필드를 클릭 외 영역 클릭 시 숨기기
document.addEventListener('click', function (event) {
    var pageInputs = document.querySelectorAll('.page-input');
    pageInputs.forEach(function(pageInput) {
        if (!pageInput.contains(event.target) && !event.target.classList.contains('dots')) {
            pageInput.classList.add("hidden");
        }
    });
});

</script>

<style>
.current {
	background-color: #1b7d6d;
	color: #ffffff;
	padding: 8px 16px;
	border-radius: 5px;
	margin: 0 2px;
}

.hidden {
	display: none;
}

.dots {
	cursor: pointer;
	position: relative;
}

.page-input {
	position: absolute;
	background-color: #1e1e1e;
	border: 1px solid #333;
	padding: 10px;
	border-radius: 4px;
	margin-top: 5px;
	z-index: 1000;
}

.page-input input[type="number"] {
	width: 50px;
	margin-right: 10px;
}

.page-input button {
	padding: 5px 10px;
	background-color: #2a9d8f;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.page-input button:hover {
	background-color: #219080;
}
</style>




</body>
</html>