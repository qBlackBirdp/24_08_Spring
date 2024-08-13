package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 없습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), article);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession session, int id, String title, String body) {
		// 로그인 상태 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}
		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 하고 써");
		}

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 없습니다.", id));
		}
		if (article.getMemberId() != loginedMemberId) 
			return ResultData.from("F-B", "게시물에 대한 권한이 없습니다.");

		articleService.modifyArticle(id, title, body);

		article = articleService.getArticleById(id);// 수정 후 데이터 새로 가져오기.
		return ResultData.from("S-2", Ut.f("%d번 게시물 수정되었습니다.", id), article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession session , int id) {
		// 로그인 상태 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}
		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 하고 써");
		}
	
		Article article = articleService.getArticleById(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 없습니다.", id));
		}
		if (article.getMemberId() != loginedMemberId) 
			return ResultData.from("F-B", "게시물에 대한 권한이 없습니다.");

		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 게시물 삭제되었습니다.", id));
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData doWrite(HttpSession session, String title, String body) {
		// 로그인 상태 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 하고 써");
		}

		if (Ut.isEmptyOrNull(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Ut.isEmptyOrNull(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}

		ResultData writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticleById(id);

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {
		List<Article> articles = articleService.getArticles();
		return ResultData.from("S-1", "Article List", articles);
	}

}