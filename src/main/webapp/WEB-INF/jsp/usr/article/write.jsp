<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 작성하기"></c:set>
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUiEditorLib.jspf"%>


    <script type="text/javascript">
        function ArticleWrite__submit(form) {
            form.title.value = form.title.value.trim();
            if (form.title.value.length == 0) {
                alert('제목을 입력하세요');
                return;
            }
            const editor = $(form).find('.toast-ui-editor').data('data-toast-editor');
            const markdown = editor.getMarkdown().trim();
            if (markdown.length == 0) {
                alert('내용을 입력하세요');
                return;
            }
            form.body.value = markdown;
            form.submit();
        }
    </script>

<section class="article-section">

	<form onsubmit="ArticleWrite__submit(this); return false;" action="../article/doWrite" method="POST">
		<input type="hidden" name="body" />

		<div class="form-group-custom">
			<label for="boardId">게시판 선택</label> <select id="boardId" name="boardId" class="form-select-custom" required>
				<option value="">게시판을 선택하세요</option>
				<option value="1">Notice</option>
				<option value="2">Free</option>
				<option value="3">QnA</option>
			</select>
		</div>

		<div class="form-group-custom">
			<label for="title">제목</label> <input type="text" id="title" name="title" value="${article.title}" required />
		</div>

		<div class="toast-ui-editor">
			<script type="text/x-template"></script>
		</div>

		<div class="form-group-custom">
			<input type="submit" value="작성하기" />
		</div>
	</form>

	<div class="actions">
		<a href="list" class="btn">취소</a>
	</div>
</section>


</body>
</html>