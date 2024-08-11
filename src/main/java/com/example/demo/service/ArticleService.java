package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.vo.Article;

@Service
public class ArticleService {

	private int lastArticleId;
	public List<Article> articles;

	public ArticleService() {
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

	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;
		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;
		return article;
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

	public void modifyArticle(int id, String title, String body) {
		Article article = findById(id);
		article.setTitle(title);
		article.setBody(body);
	}

	public void deleteArticle(int id) {
		Article article = findById(id);
		articles.remove(article);
	}
}
