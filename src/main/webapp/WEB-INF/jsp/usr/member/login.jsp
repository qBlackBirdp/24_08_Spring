<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="로그인"></c:set>
<%@ include file="../common/head.jspf"%>

<hr />

<section class="">
	<div class="">
		<form action="../member/doLogin" method="POST">
		<table class="login-table">
			<tbody>
				<tr>
					<th>아이디</th>
					<td><input name="loginId" autocomplete="off" type="text"
						placeholder="아이디를 입력해" /></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input name="loginPw"
						autocomplete="off" type="text" placeholder="비밀번호를 입력해" /></td>

				</tr>
				<tr>
					<th></th>
					<td><input type="submit"
						value="로그인" /></td>

				</tr>
			</tbody>
		</table>
		</form>
	</div>
</section>

</body>
</html>