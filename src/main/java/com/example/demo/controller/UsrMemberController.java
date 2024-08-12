package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		if (Ut.isEmptyOrNull(loginId))
			return ResultData.from("F-1", Ut.f("아이디를 입력해주세요."));

		if (Ut.isEmptyOrNull(loginPw))
			return ResultData.from("F-2", Ut.f("비밀번호를 입력해주세요."));

		if (Ut.isEmptyOrNull(name))
			return ResultData.from("F-3", Ut.f("이름을 입력해주세요."));

		if (Ut.isEmptyOrNull(nickname))
			return ResultData.from("F-4", Ut.f("닉네임를 입력해주세요."));

		if (Ut.isEmptyOrNull(cellphoneNum))
			return ResultData.from("F-5", Ut.f("전화번호를 입력해주세요."));

		if (Ut.isEmptyOrNull(email))
			return ResultData.from("F-6", Ut.f("이메일을 입력해주세요."));
		
		ResultData doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (doJoinRd.isFail()) {
			return doJoinRd;
		}

		Member member = memberService.getMemberById((int) doJoinRd.getData1());

		return ResultData.newData(doJoinRd, member);
	}

//	@RequestMapping("/usr/member/doLogin")
//	@ResponseBody
//	public ResultData doLogin(String loginId, String loginPw) {
//		
//		if (Ut.isEmptyOrNull(loginId))
//			return ResultData.from("F-1", Ut.f("아이디를 입력해주세요."));
//
//		if (Ut.isEmptyOrNull(loginPw))
//			return ResultData.from("F-2", Ut.f("비밀번호를 입력해주세요."));
//		
//		int id = memberService.doLogin(loginId, loginPw);
//		Member member = memberService.getMemberById(id);
//	    if (member == null) {
//	        return ResultData.from("F-3", "아이디 또는 비밀번호가 일치하지 않습니다.");
//	    }
//		
//		return ResultData.from("S-1", Ut.f("%s님 환영합니다.", memberService.getNickname(id)), member);
//	}

}