package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	private int memberId;
	private int boardId;
	
	private int hitCount;
	private String extra__writer;
	private int extra__repliesCount;
	
	private boolean UserCanModify;
	private boolean UserCanDelete;
	
	private int sumReactionPoint;
	private int goodReactionPoint;
	private int badReactionPoint;
	
	private int page;
	
	private int replyCount;
    
}