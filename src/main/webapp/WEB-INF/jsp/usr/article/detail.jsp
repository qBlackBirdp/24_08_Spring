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
$(document).ready(function() {
    const articleId = ${article.id};
    const relTypeCode = 'article';
    const relId = articleId;

    // 서버에서 전달받은 사용자 반응 상태
    const userReactionPoint = ${userReactionPoint}; // 1: 좋아요, -1: 싫어요, 0: 반응 없음

    // 페이지 로드 시 버튼 초기화
    if (userReactionPoint === 1) {
        $('#likeBtn').addClass('liked');
    } else if (userReactionPoint === -1) {
        $('#disLikeBtn').addClass('disliked');
    }
    function reaction(point) {
        // jQuery의 AJAX 메서드를 사용하여 서버와 비동기 통신
        $.post('/article/doReaction', {
            id: articleId,
            relTypeCode: relTypeCode,
            relId: relId,
            newPoint: point
        }, function(response) {
        	console.log(response);
            // 서버 응답 처리
            if (response.resultCode.startsWith("S-")) {
            
                // 좋아요/싫어요 개수 업데이트
                $('.likeCount').text(response.data2.goodReactionPoint);
                $('.disLikeCount').text(response.data2.badReactionPoint);

                // UI 업데이트
                updateReactionUI(point);
            } else {
                alert(response.msg); // 오류 메시지 출력
            }
        });
    }

    function updateReactionUI(point) {
        if (point > 0) {
            // 좋아요 설정
            $('#likeBtn').addClass('liked');
            $('#disLikeBtn').removeClass('disliked');
        } else if (point < 0) {
            // 싫어요 설정
            $('#disLikeBtn').addClass('disliked');
            $('#likeBtn').removeClass('liked');
        } else {
            // 반응 취소 (좋아요 또는 싫어요)
            $('#likeBtn').removeClass('liked');
            $('#disLikeBtn').removeClass('disliked');
        }
    }

    // 좋아요 버튼 클릭 시
    $('#likeBtn').on('click', function() {
        if ($(this).hasClass('liked')) {
            reaction(0); // 좋아요 취소
        } else {
            reaction(1); // 좋아요 설정
        }
    });

    // 싫어요 버튼 클릭 시
    $('#disLikeBtn').on('click', function() {
        if ($(this).hasClass('disliked')) {
            reaction(0); // 싫어요 취소
        } else {
            reaction(-1); // 싫어요 설정
        }
    });
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
    	<span class="label">LIKE </span> <span class="likeCount">${article.goodReactionPoint}</span>

    	<span class="label">DISLIKE</span> <span class="disLikeCount">${article.badReactionPoint}</span>
	</div>
	<div class="detail-item">
		<button id="likeBtn">
			<i class="far fa-thumbs-up"></i> 좋아요 <span class="likeCount">${article.goodReactionPoint}</span>
		</button>
		<button id="disLikeBtn">
			<i class="far fa-thumbs-down"></i> 싫어요 <span class="disLikeCount">${article.badReactionPoint}</span>
		</button>
	</div>
	<!-- 	👍<span id="likeCount" class="like-count">0</span>
 -->
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