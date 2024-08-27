package com.example.demo.vo;

import lombok.Data;

@Data
public class Reply {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String relTypeCode;
	private int relId;
	private String body;
	private int goodReactionPoint;
	private int badReactionPoint;

	private String extra__writer;

	private String extra__sumReactionPoint;

	private boolean userCanModify;
	private boolean userCanDelete;
}
