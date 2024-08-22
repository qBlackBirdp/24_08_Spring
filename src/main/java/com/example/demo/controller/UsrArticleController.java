package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import com.example.demo.service.ReactionPointService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Board;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private Rq rq;

	@Autowired
	private BoardService boardService;
	
	@Autowired
    private ReactionPointService reactionPointService;

	@RequestMapping("/usr/article/detail")
	public String showDetail( Model model, int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);
		
		int likeCount = reactionPointService.getTotalReactionPoints("article", id);
        model.addAttribute("likeCount", likeCount);

		return "usr/article/detail";
	}
	@RequestMapping("/article/doReaction")
    @ResponseBody
    public Map<String, Object> doReaction(HttpServletRequest req ,@RequestParam int id, @RequestParam String relTypeCode, @RequestParam int relId) {
		Rq rq = (Rq) req.getAttribute("rq");
		
        int resultPoint = reactionPointService.toggleReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", resultPoint > 0 ? "liked" : "unliked");
        return response;
    }
	
	
	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData doIncreaseHitCount(int id) {

		ResultData increaseHitCountRd = articleService.increaseHitCount(id);

		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}
		ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));

		rd.setData2("조회수가 증가된 게시글의 id", id);

		return rd;
	}

	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {
		Article article = articleService.getArticleById(id);

		if (article == null) {
			return "redirect:/usr/article/list";
		}

		model.addAttribute("article", article);
		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return Ut.jsReplace("F-1", Ut.f("%d번 게시글은 없습니다", id), " / ");
		}

		ResultData userCanModifyRd = articleService.userCanModify(rq.getLoginedMemberId(), article);

		if (userCanModifyRd.isFail()) {
			return Ut.jsHistoryBack(userCanModifyRd.getResultCode(), userCanModifyRd.getMsg());
		}

		if (userCanModifyRd.isSuccess()) {
			articleService.modifyArticle(id, title, body);
		}

		article = articleService.getArticleById(id);

		return Ut.jsReplace(userCanModifyRd.getResultCode(), userCanModifyRd.getMsg(), "/usr/article/detail?id=" + id);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticleById(id);

		if (article == null) {
//			return ResultData.from("F-1", Ut.f("%d번 게시글은 없습니다", id), "입력한 id", id);
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 게시글은 없습니다", id));
		}

		ResultData userCanDeleteRd = articleService.userCanDelete(rq.getLoginedMemberId(), article);

		if (userCanDeleteRd.isFail()) {
			return Ut.jsHistoryBack(userCanDeleteRd.getResultCode(), userCanDeleteRd.getMsg());
		}

		if (userCanDeleteRd.isSuccess()) {
			articleService.deleteArticle(id);
		}

		return Ut.jsReplace(userCanDeleteRd.getResultCode(), userCanDeleteRd.getMsg(), "../article/list");
	}

	@RequestMapping("/usr/article/write")
	public String showWrite(HttpServletRequest req) {

		return "usr/article/write";
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body, String boardId) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.isEmptyOrNull(title)) {
			return Ut.jsHistoryBack("F-1", "제목을 입력해주세요");
		}
		if (Ut.isEmptyOrNull(body)) {
			return Ut.jsHistoryBack("F-2", "내용을 입력해주세요");
		}
		if (Ut.isEmptyOrNull(boardId)) {
			return Ut.jsHistoryBack("F-3", "게시판 선택해주세요.");
		}

		int boardIdInt = Integer.parseInt(boardId);
		ResultData writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body, boardIdInt);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticleById(id);

		return Ut.jsReplace(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), "/usr/article/detail?id=" + id);
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, HttpServletRequest req,
	                       @RequestParam(defaultValue = "1") Integer page, 
	                       @RequestParam(defaultValue = "1") int boardId,
	                       @RequestParam(value = "searchField", required = false) String searchField,
	                       @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
		
		Rq rq = (Rq) req.getAttribute("rq");
		
		if (page == null) {
		    page = 1; // 기본값 설정
		}

	    Board board = boardService.getBoardById(boardId);
	    model.addAttribute("board", board);

	    int itemsPerPage = 10;
	    int offset = (page - 1) * itemsPerPage;
	    List<Article> articles;
	    int totalItems;
	    int totalPages;

	    if (searchField != null && searchKeyword != null && !searchKeyword.trim().isEmpty()) {
	        totalItems = articleService.getTotalArticlesCountBySearch(boardId, searchField, searchKeyword);
	        articles = articleService.getArticlesByPageAndSearch(boardId, searchField, searchKeyword, itemsPerPage, offset);
	    } else {
	        totalItems = articleService.getTotalArticlesCount(boardId);
	        articles = articleService.getArticlesByPage(boardId, offset, itemsPerPage);
	    }

	    totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

	    model.addAttribute("articles", articles);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("boardId", boardId);
	    model.addAttribute("searchField", searchField);
	    model.addAttribute("searchKeyword", searchKeyword);

	    return "usr/article/list";
	}
}