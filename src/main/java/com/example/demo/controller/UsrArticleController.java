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
		
		makeTestData();
	}
	
	// 테스트데이터 생성.
	private void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}
	}

	private Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;
		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;
		return article;
	}
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
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
    @RequestMapping("/usr/article/doDelete")
    @ResponseBody
    public  String DoDeleteArticle(int id) {
    	Article article = findById(id);
    	
    	if (article == null) {
    		return id + "번 게시물 존재하지 않음.";
    	} else articles.remove(article);
    	
    	
    	return id + "번 게시물 삭제.";
    }
    
    //게시물 상세보기.
    @RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticle(int id) {

		Article article = findById(id);

		if (article == null) {
			return id + "번 글은 없음";
		}

		return article;
	}
    //게시물 수정.
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {

		Article article = findById(id);

		if (article == null) {
			return id + "번 글은 없음";
		} else {
			article.setTitle(title);
			article.setBody(body);
		}

		return article;
	}

}