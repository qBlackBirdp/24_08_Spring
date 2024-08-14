package com.example.demo.service;

import org.springframework.ui.Model;

import com.example.demo.vo.Article;

import jakarta.servlet.http.HttpSession;

public class AuthService {

	public String checkLogin(HttpSession session, Model model) {
		if (session.getAttribute("loginedMemberId") == null) {
			model.addAttribute("errorMessage", "로그인 먼저 해주세요.");
			return "usr/member/login";
		}
		return null;
	}

	public String checkArticlePermission(HttpSession session, Model model, Article article) {
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");
		if (article == null) {
			model.addAttribute("errorMessage", "해당 게시물은 없습니다.");
			return "usr/article/list";
		}
		if (article.getMemberId() != loginedMemberId) {
			model.addAttribute("errorMessage", "게시물에 대한 권한이 없습니다.");
			return "usr/article/list";
		}
		return null;
	}

}
