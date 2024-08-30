package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("member")
public class UsrMemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "/usr/member/join";
	}
	
	@RequestMapping("/member/form")
    public String showMemberForm(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);  // 이 "member" 속성이 세션에 저장됩니다.
        return "memberForm";
    }

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(HttpServletRequest req, String loginId, String loginPw,
			String name, String nickname, String cellphoneNum, String email) {
		rq = (Rq) req.getAttribute("rq");
		
		if (Ut.isEmptyOrNull(loginId))
			return Ut.jsHistoryBack("F-1", Ut.f("아이디를 입력해주세요."));

		if (Ut.isEmptyOrNull(loginPw))
			return Ut.jsHistoryBack("F-2", Ut.f("비밀번호를 입력해주세요."));

		if (Ut.isEmptyOrNull(name))
			return Ut.jsHistoryBack("F-3", Ut.f("이름을 입력해주세요."));

		if (Ut.isEmptyOrNull(nickname))
			return Ut.jsHistoryBack("F-4", Ut.f("닉네임를 입력해주세요."));

		if (Ut.isEmptyOrNull(cellphoneNum))
			return Ut.jsHistoryBack("F-5", Ut.f("전화번호를 입력해주세요."));

		if (Ut.isEmptyOrNull(email))
			return Ut.jsHistoryBack("F-6", Ut.f("이메일을 입력해주세요."));

		ResultData doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (doJoinRd.isFail()) {
			return Ut.jsHistoryBack(doJoinRd.getResultCode(), doJoinRd.getMsg());
		}

		Member member = memberService.getMemberById((int) doJoinRd.getData1());

		return Ut.jsReplace(doJoinRd.getResultCode(), doJoinRd.getMsg(), "/usr/member/login");
	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpServletRequest req) {
		return "/usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw, String afterLoginUri) {

		rq = (Rq) req.getAttribute("rq");

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는(은) 존재 하지않습니다.", loginId));
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호가 틀렸습니다."));
		}

		rq.login(member);
		
		if(afterLoginUri.length() > 0) return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), afterLoginUri);

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), " / ");
	}

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req, SessionStatus sessionStatus) {

		// 로그아웃 처리
		rq.logout();
		sessionStatus.setComplete();

		return Ut.jsReplace("S-1", Ut.f("로그아웃 성공"), " / ");
	}
	
	@RequestMapping("/usr/member/myPage")
	public String showmyPage() {
		return "usr/member/myPage";
	}

	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {
		return "usr/member/checkPw";
	}
	
//	@RequestMapping("/usr/member/doCheckPw")
//	@ResponseBody
//	public String doCheckPw(HttpServletRequest req, String loginPw) {
//		
//		rq = (Rq) req.getAttribute("rq");
//		
//		System.out.println(1);
//		System.out.println(rq.getLoginedMember());
//		System.out.println(rq.getLoginedMember().getLoginPw());
//		
//		
//		if (Ut.isEmptyOrNull(loginPw)) {
//			return Ut.jsHistoryBack("F-1", "비밀번호 작성하지 않음.");
//		} if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
//			return Ut.jsHistoryBack("F-2", "비밀번호 일치하지 않음.");
//		}
//
//		return Ut.jsReplace("S-1", Ut.f("비밀번호 확인 성공"), "modify");
//	}

	@RequestMapping("/usr/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(HttpServletRequest req, String loginPw) {
	
		rq = (Rq) req.getAttribute("rq");
		
		System.out.println(1);
		System.out.println(rq.getLoginedMember());
		System.out.println(rq.getLoginedMember().getLoginPw());
				
		
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-1", "비밀번호 작성하지 않음.");
		} if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-2", "비밀번호 일치하지 않음.");
		}

		return Ut.jsReplace("S-1", Ut.f("비밀번호 확인 성공"), "modify");
	}
	@RequestMapping("/usr/member/modify")
	public String showmyModify() {
		
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		Rq rq = (Rq) req.getAttribute("rq");
		System.out.println(loginPw + name + nickname + cellphoneNum + email);

		// 비번은 안바꾸는거 가능(사용자) 비번 null 체크는 x

		if (Ut.isEmptyOrNull(name)) {
			return Ut.jsHistoryBack("F-3", "name 입력 x");
		}
		if (Ut.isEmptyOrNull(nickname)) {
			return Ut.jsHistoryBack("F-4", "nickname 입력 x");
		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return Ut.jsHistoryBack("F-5", "cellphoneNum 입력 x");
		}
		if (Ut.isEmptyOrNull(email)) {
			return Ut.jsHistoryBack("F-6", "email 입력 x");
		}

		ResultData modifyRd;
		
		

		if (Ut.isEmptyOrNull(loginPw)) {
			modifyRd = memberService.modifyWithoutPw(rq.getLoginedMemberId(), name, nickname, cellphoneNum, email);
		} else {
			modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum, email);
		}
		
		System.out.println("Before calling modifyWithoutPw or modify");
		if (Ut.isEmptyOrNull(loginPw)) {
		    modifyRd = memberService.modifyWithoutPw(rq.getLoginedMemberId(), name, nickname, cellphoneNum, email);
		} else {
		    modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum, email);
		}
		System.out.println("modifyRd: " + modifyRd);
		
		
		return Ut.jsReplace(modifyRd.getResultCode(), modifyRd.getMsg(), "../member/myPage");
	}
}