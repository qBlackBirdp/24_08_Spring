package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(HttpSession session, String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		// 이미 로그인된 상태인지 확인
        if (session.getAttribute("loginUser") != null) 
            return ResultData.from("F-0", "이미 로그인된 상태입니다.");
        
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

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData<Member> doLogin(HttpServletRequest request, String loginId, String loginPw) {
		HttpSession session = request.getSession();
		// 이미 로그인된 상태인지 확인
        if (session.getAttribute("loginUser") != null) {
            return ResultData.from("F-0", "이미 로그인된 상태입니다.");
        }
        
	    if (Ut.isEmptyOrNull(loginId)) 
	        return ResultData.from("F-1", "아이디를 입력해주세요.");
	    

	    if (Ut.isEmptyOrNull(loginPw)) 
	        return ResultData.from("F-2", "비밀번호를 입력해주세요.");
	    
	    
	    ResultData<Member> loginRd = memberService.doLogin(loginId, loginPw);

        if (loginRd.isFail()) return loginRd;
        
	    
     // 로그인 성공 시 세션에 사용자 정보 저장
        Member member = loginRd.getData1();
        session.setAttribute("loginUser", member);
        session.setAttribute("loginedMemberId", member.getId()); // memberId를 세션에 저장

	    return memberService.doLogin(loginId, loginPw);
	}
	@RequestMapping("/usr/member/doLogout")
    @ResponseBody
    public ResultData<Void> doLogout(HttpSession session) {
		// 로그인 상태 확인
        if (session.getAttribute("loginUser") == null) 
            return ResultData.from("F-0", "로그인 상태가 아닙니다.");
        
		
        // 로그아웃 처리
        session.invalidate();

        return ResultData.from("S-1", "로그아웃 되었습니다.");
    }
}