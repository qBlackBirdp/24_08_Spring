package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.vo.Article;

@Controller
public class UsrArticleController {

	int lastArticleId;
	List<Article> articles;

	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = lastArticleId + 1;
		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles;
	}

	// 테스트데이터 생성.
	@RequestMapping("/usr/article/getTestArticle")
	@ResponseBody
	public List<Article> getArticle() {
	    
	    for (int i = 1; i <= 10; i++) {
	        Article article = new Article(i, "제목" + i, "내용" + i);
	        articles.add(article);
	    }
	    
	    return articles;
	}
	// 입력된 id와 일치하는 article 찾기
	@RequestMapping("/usr/article/findById")
	@ResponseBody
    public Article findById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }
    //게시물 삭제.
    @RequestMapping("/usr/article/doDeleteArticle")
    @ResponseBody
    public  String DoDeleteArticle(int id) {
    	Article article = findById(id);
    	
    	if (article == null) {
    		return id + "번 게시물 존재하지 않음.";
    	} else articles.remove(article);
    	
    	
    	return id + "번 게시물 삭제.";
    }
    
    //게시물 수정.
    @RequestMapping("/usr/article/doModifyArticle")
    @ResponseBody
    public  String DoModifyArticle(int id, String newTitle, String newBody) {
    	Article article = findById(id);
    	
    	if (article == null) {
    		return id + "번 게시물 존재하지 않음.";
    	} else {
    		article.setTitle(newTitle);
    		article.setBody(newBody);
    	}
    	//doModifyArticle?id=1%newTitle=%EC%83%88%EC%A0%9C%EB%AA%A9&newBody=%EC%83%88%EB%82%B4%EC%9A%A9
    	
    	return id + "번 게시물 수정.";
    }
    

}