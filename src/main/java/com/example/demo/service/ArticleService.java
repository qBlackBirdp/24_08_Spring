package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.vo.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	private int lastArticleId;
	public List<Article> articles;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;

		makeTestData();
	}

	// 테스트데이터 생성.
	private void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			articleRepository.writeArticle(title, body);
		}
	}

	public Article writeArticle(String title, String body) {
		return articleRepository.writeArticle(title, body);
	}

	// 입력된 id와 일치하는 article 찾기
	@RequestMapping("/usr/article/findById")
	@ResponseBody
	public Article findById(int id) {
		return articleRepository.getArticleById(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}
	
	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}
}
