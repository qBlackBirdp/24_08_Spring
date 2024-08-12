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

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		if (Ut.isEmptyOrNull(loginId)) return "아이디를 입력해주세요.";
		
		if (Ut.isEmptyOrNull(loginPw)) return "비밀번호를 입력해주세요.";
		
		if (Ut.isEmptyOrNull(name)) return "이름을 입력해주세요.";
		
		if (Ut.isEmptyOrNull(nickname)) return "닉네임를 입력해주세요.";
		
		if (Ut.isEmptyOrNull(cellphoneNum)) return "전화번호를 입력해주세요.";
		
		if (Ut.isEmptyOrNull(email)) return "이메일을 입력해주세요.";
		
		
		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		Member member = memberService.getMemberById(id);

		if (id == -1) return Ut.f("%s(은)는 이미 사용중인 아이디입니다.", loginId);
		if (id == -2) return Ut.f("%s(은)는 이미 사용중인 닉네임입니다.", nickname);
		if (id == -3) return Ut.f("%s(은)는 이미 사용중인 이메일입니다.", email);
		if (id == -4) return Ut.f("%s(은)는 이미 사용중인 이메일과 이름입니다.", email, name);
		if (id == -5) return Ut.f("%s와 %s(은)는 이미 사용중인 전화번호와 이름입니다.", cellphoneNum, name);
		
		return member;
	}

}