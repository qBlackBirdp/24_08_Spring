package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public ResultData writeArticle(int memberId, String title, String body, int boardId) {

		articleRepository.writeArticle(memberId, title, body, boardId);

		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 글이 등록되었습니다", id), "등록 된 게시글의 id", id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public Article getArticleById(int id) {

		return articleRepository.getArticleById(id);
	}

	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

	public Article getForPrintArticle(int loginedMemberId, int id) {

		Article article = articleRepository.getForPrintArticle(id);

		controlForPrintData(loginedMemberId, article);

		return article;
	}

	private void controlForPrintData(int loginedMemberId, Article article) {
		if (article == null) {
			return;
		}
		ResultData userCanModifyRd = userCanModify(loginedMemberId, article);
		article.setUserCanModify(userCanModifyRd.isSuccess());

		ResultData userCanDeleteRd = userCanDelete(loginedMemberId, article);
		article.setUserCanDelete(userCanModifyRd.isSuccess());
	}

	public ResultData userCanDelete(int loginedMemberId, Article article) {
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 게시글에 대한 삭제 권한이 없습니다", article.getId()));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시글을 삭제했습니다", article.getId()));
	}

	public ResultData userCanModify(int loginedMemberId, Article article) {
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 게시글에 대한 수정 권한이 없습니다", article.getId()));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시글을 수정했습니다", article.getId()), "수정된 게시글", article);
	}

	public List<Article> getBIdArticles(int boardId) {
		return articleRepository.getArticlesByBorderId(boardId);
	}

	// 페이지네이션
	public List<Article> getArticlesByPage(int boardId, int offset, int limit) {
		return articleRepository.getArticlesByPage(boardId, offset, limit);
	}

	public int getTotalArticlesCount(int boardId) {
		return articleRepository.getTotalArticlesCount(boardId);
	}
	//검색기능.
	public int getTotalArticlesCountBySearch(int boardId, String searchField, String searchKeyword) {
		return articleRepository.getTotalArticlesCountBySearch(boardId, searchField, searchKeyword);
	}

	public List<Article> getArticlesByPageAndSearch(int boardId, String searchField, String searchKeyword, int offset, int limit) {
	    return articleRepository.getArticlesByPageAndSearch(boardId, searchField, searchKeyword, offset, limit);
	}
}