<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 수정하기"></c:set>
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUiEditorLib.jspf"%>

<script type="text/javascript">
	function ArticleModify__submit(form) {
		form.title.value = form.title.value.trim();
		if (form.title.value.length == 0) {
			alert('제목을 입력해주세요');
			return;
		}
		const editor = $(form).find('.toast-ui-editor').data(
				'data-toast-editor');
		const markdown = editor.getMarkdown().trim();
		if (markdown.length == 0) {
			alert('내용 써');
			editor.focus();
			return;
		}
		form.body.value = markdown;
		form.submit();
	}
</script>

<section class="article-section">

	<form onsubmit="ArticleModify__submit(this); return false;" action=" ../article/doModify" method="POST">
		<input type="hidden" name="id" value="${article.id}" /> <input type="hidden" name="body">

		<div class="form-group">
			<label for="title">제목</label> <input name="title" value="${article.title}" type="text" autocomplete="off" placeholder="새 제목을 입력해" class="input input-bordered input-primary w-full max-w-xs input-sm " />
		</div>

		<div class="form-group">
			<label for="body">내용</label>
			<div class="toast-ui-editor">
				<script type="text/x-template">${article.body }</script>
			</div>
		</div>
			<div class="form-group">
				<input type="submit" value="수정하기" />
			</div>
	</form>

	<div class="actions">
		<a href="detail?id=${article.id}" class="btn">취소</a>
	</div>
</section>


</body>
</html>