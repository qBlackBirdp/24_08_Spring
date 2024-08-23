<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시물 상세보기"></c:set>
<%@ include file="../common/head.jspf"%>

<script>
	const params = {};
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__doIncreaseHitCount() {
		//로컬스토리지, 조회수 조작 방지.
		const localStorageKey = 'article__' + params.id + '__alreadyOnView';
		if (localStorage.getItem(localStorageKey)) {
			return;
		}
		localStorage.setItem(localStorageKey, true);

		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			console.log(data);
			console.log(data.data1);
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json')
	}
	$(function() {
		ArticleDetail__doIncreaseHitCount();
		//setTimeout(ArticleDetail__doIncreaseHitCount, 2000);
	})
</script>
<script>
	function reaction(point) {
		const articleId = ${article.id};
		const relTypeCode = 'article';
		const relId = articleId;

		$.post('/article/doReaction', {
			id : articleId,
			relTypeCode : relTypeCode,
			relId : relId,
			newPoint : point
		// 좋아요는 1, 싫어요는 -1로 설정
		}, function(response) {
			if (response.status === 'liked') {
				alert('게시물 좋아요.');
			} else if (response.status === 'unliked') {
				alert('게시물 좋아요 취소.');
			} else if (response.status === 'disliked') {
				alert('게시물 싫어요.');
			} else if (response.status === 'undisliked') {
				alert('게시물 싫어요 취소.');
			}
		});

	// 좋아요 버튼 클릭 시
	$('#likeBtn').on('click', function() {
		reaction(1); // 좋아요는 1로 설정
	});

	// 싫어요 버튼 클릭 시
	$('#dislikeBtn').on('click', function() {
		reaction(-1); // 싫어요는 -1로 설정
	});
</script>


<hr />

<div class="article-detail">
	<div class="detail-item">
		<span class="label">게시판:</span>
		<c:choose>
			<c:when test="${article.boardId == 1}">Notice</c:when>
			<c:when test="${article.boardId == 2}">Free</c:when>
			<c:when test="${article.boardId == 3}">QnA</c:when>
			<c:otherwise>Unknown</c:otherwise>
		</c:choose>
	</div>
	<div class="detail-item">
		<span class="label">번호:</span> ${article.id}
	</div>
	<div class="detail-item">
		<span class="label">조회수: <span
			class=" article-detail__hit-count">${article.hitCount}</span></span>
	</div>
	<div class="detail-item">
		<span class="label">날짜:</span> ${article.regDate}
	</div>
	<div class="detail-item">
		<span class="label">수정 날짜:</span> ${article.updateDate}
	</div>
	<div class="detail-item">
		<span class="label">작성자:</span> ${article.extra__writer}
	</div>
	<div class="detail-item">
		<span class="label">제목:</span> ${article.title}
	</div>
	<div class="detail-item">
		<span class="label">내용:</span> ${article.body}
	</div>
	<div>
		<span class="label">Sum</span>${article.extra__sumReactionPoint}
	</div>
	<div>
		<span class="label">LIKE</span>${article.extra__goodReactionPoint}
	</div>
	<div>
		<span class="label">Bad</span>${article.extra__badReactionPoint}
	</div>
	<c:if test="${rq.isLogined()}">
		<button id="likeBtn">👍</button>
		<button id="disLikeBtn">👎</button>
	</c:if>
	👍<span id="likeCount" class="like-count">0</span>

	<div class="actions">
		<c:if test="${article.userCanModify}">
			<a href="../article/modify?id=${article.id}" class="btn">게시물 수정</a>
		</c:if>
		<c:if test="${article.userCanDelete}">
			<a href="../article/doDelete?id=${article.id}" class="btn">게시물 삭제</a>
		</c:if>
	</div>
</div>

<div class="navigation">
	<a href="../home/main" class="btn ml-2 mr-2">메인 페이지로</a> <a href="list"
		class="btn">리스트로 돌아가기</a>
</div>


</body>
</html>