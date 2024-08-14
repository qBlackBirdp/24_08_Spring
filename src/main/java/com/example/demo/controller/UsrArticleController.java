package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.service.AuthService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
    private AuthService authService;

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, HttpSession session, int id) {
		
		Article article = articleService.getArticleById(id);

		if (article == null) {
			model.addAttribute("errorMessage", "해당 게시물은 없습니다.");
			return "/usr/article/list";
//			return ResultData.from("F-1", Ut.f("%d번 게시물은 없습니다.", id));
		}
		model.addAttribute("article", article);

		return "/usr/article/detail";
	}

	@RequestMapping("/usr/article/modify")
	public String doModify(Model model, HttpSession session, int id, String title, String body) {
		// 로그인 상태 확인
        String loginCheck = authService.checkLogin(session, model);
        if (loginCheck != null) {
            return loginCheck;
        }

        // 게시물 가져오기 및 권한 확인
        Article article = articleService.getArticleById(id);
        String permissionCheck = authService.checkArticlePermission(session, model, article);
        if (permissionCheck != null) {
            return permissionCheck;
        }

		articleService.modifyArticle(id, title, body);

		article = articleService.getArticleById(id);// 수정 후 데이터 새로 가져오기.
		return "/usr/article/modify";
	}

//	public void isLogined(HttpSession session, Model model) {
//		// 로그인 상태 확인
//		boolean isLogined = false;
//		int loginedMemberId = 0;
//
//		if (session.getAttribute("loginedMemberId") != null) {
//			isLogined = true;
//			loginedMemberId = (int) session.getAttribute("loginedMemberId");
//		}
//		if (isLogined == false) {
//			model.addAttribute("errorMessage", "로그인먼저 해주세요.");
//			return "usr/member/login";
////					return ResultData.from("F-A", "로그인 하고 써");
//		}
//		Article article = articleService.getArticleById(id);
//
//		if (article == null) {
//			model.addAttribute("errorMessage", "해당 게시물은 없습니다.");
//			return "usr/article/list";
////			return ResultData.from("F-1", Ut.f("%d번 게시물은 없습니다.", id));
//		}
//		if (article.getMemberId() != loginedMemberId) {
//			model.addAttribute("errorMessage", "게시물에 대한 권한이 없습니다.");
//			return "usr/article/list";
////			return ResultData.from("F-B", "게시물에 대한 권한이 없습니다.");
//		}
//	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(Model model, HttpSession session, int id) {
		
		// 로그인 상태 확인
        String loginCheck = authService.checkLogin(session, model);
        if (loginCheck != null) {
            return loginCheck;
        }

        // 게시물 가져오기 및 권한 확인
        Article article = articleService.getArticleById(id);
        String permissionCheck = authService.checkArticlePermission(session, model, article);
        if (permissionCheck != null) {
            return permissionCheck;
        }

		articleService.deleteArticle(id);

		return "/usr/article/list";
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

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), "생성된 게시글", article);
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getArticles();

		model.addAttribute("articles", articles);
		return "/usr/article/list";
	}

}