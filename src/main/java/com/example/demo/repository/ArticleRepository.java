package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.Article;

@Mapper
public interface ArticleRepository {

//	@Insert("INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = #{title}, `body` = #{body}")
	public void writeArticle(int memberId, String title, String body, int boardId);

	@Delete("DELETE FROM article WHERE id = #{id}")
	public void deleteArticle(int id);

//	@Update("UPDATE article SET updateDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}")
	public void modifyArticle(int id, String title, String body);

//	@Select("SELECT * FROM article WHERE id = #{id}")
	public Article getArticleById(int id);

//	@Select("SELECT * FROM article ORDER BY id DESC")
	public List<Article> getArticles();

	@Select("SELECT LAST_INSERT_ID();")
	public int getLastInsertId();

	@Select("""
			SELECT a.* , m.nickname AS extra__writer
			FROM article AS a
			INNER JOIN `member` AS m
			ON a.memberId = m.id
			WHERE a.id = #{id}
				""")
	public Article getForPrintArticle(int id);
	
	@Select("""
			SELECT a.*, m.nickname AS extra__writer
			FROM article a
			INNER JOIN `member` m
			ON a.memberId = m.id
			WHERE a.boardId = #{boardId}
			ORDER BY
			a.id DESC
				""")
	public List<Article> getArticlesByBorderId(int boardId);

	//페이지네이션
	public List<Article> getArticlesByPage(int boardId, int offset, int limit);

	public int getTotalArticlesCount(int boardId);
	
	//검색기능 
//	@Select("""
//		    SELECT COUNT(*)
//		    FROM article
//		    WHERE boardId = #{boardId}
//		    AND ${searchField} LIKE CONCAT('%', #{searchKeyword}, '%')
//		""")
	public int getTotalArticlesCountBySearch(int boardId, String searchField, String searchKeyword);
	
//	@Select("""
//		    SELECT *
//		    FROM article
//		    WHERE boardId = #{boardId}
//		    AND ${searchField} LIKE CONCAT('%', #{searchKeyword}, '%')
//		    ORDER BY id DESC
//		    LIMIT #{limit} OFFSET #{offset}
//		""")
	public List<Article> getArticlesByPageAndSearch(int boardId, String searchField, String searchKeyword, int limit,
			int offset);
	
	
}