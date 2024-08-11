package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.vo.Article;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;
	
	
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = articleService.writeArticle(title, body);
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articleService.articles;
	}

	
    //게시물 삭제.
    @RequestMapping("/usr/article/doDelete")
    @ResponseBody
    public  String DoDeleteArticle(int id) {
    	Article article = articleService.findById(id);
    	
    	if (article == null) {
    		return id + "번 게시물 존재하지 않음.";
    	} else articleService.articles.remove(article);
    	
    	
    	return id + "번 게시물 삭제.";
    }
    
    //게시물 상세보기.
    @RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticle(int id) {

		Article article = articleService.findById(id);

		if (article == null) {
			return id + "번 글은 없음";
		}

		return article;
	}
    //게시물 수정.
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {

		Article article = articleService.findById(id);

		if (article == null) {
			return id + "번 글은 없음";
		} else {
			article.setTitle(title);
			article.setBody(body);
		}

		return article;
	}

}