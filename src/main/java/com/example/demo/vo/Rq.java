package com.example.demo.vo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

public class Rq {
	
	@Getter
	private boolean isLogined = false;
	@Getter
	private int loginedMemberId = 0;
	
	public Rq(HttpServletRequest req) {
		
		HttpSession httpSession = req.getSession();
		
		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
	}

	
}
