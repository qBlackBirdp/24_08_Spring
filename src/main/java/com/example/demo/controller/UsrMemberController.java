package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;

@Controller
public class UsrMemberController {
	
	@Autowired
	private MemberService memberService;
	private Ut ut;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		if (ut.isEmptyOrNull(loginId)) return "아이디를 입력해주세요.";
		
		if (ut.isEmptyOrNull(loginPw)) return "비밀번호를 입력해주세요.";
		
		if (ut.isEmptyOrNull(name)) return "이름을 입력해주세요.";
		
		if (ut.isEmptyOrNull(nickname)) return "닉네임를 입력해주세요.";
		
		if (ut.isEmptyOrNull(cellphoneNum)) return "전화번호를 입력해주세요.";
		
		if (ut.isEmptyOrNull(email)) return "이메일을 입력해주세요.";
		
		
		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		Member member = memberService.getMemberById(id);

		if (id == -1) return "이미 사용중인 아이디";
		if (id == -2) return "이미 사용중인 닉네임";
		if (id == -3) return "이미 사용중인 이메일";
		if (id == -4) return "이미 사용중인 이름과 이메일";
		
		return member;
	}

}