package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public ResultData<Integer> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {

		Member existMember = memberRepository.getMemberByLoginId(loginId);
		if (existMember != null) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다.", loginId));
		}
		
		existMember = memberRepository.getMemberByNickname(nickname);
		if (existMember != null) return ResultData.from("F-8", Ut.f("이미 사용중인 닉네임(%s)입니다.", nickname));
		
		existMember = memberRepository.getMemberByEmail(email);
		if (existMember != null) return ResultData.from("F-9", Ut.f("이미 사용중인 이메일(%s)입니다.", email));
		
		existMember = memberRepository.getMemberByNameAndEmail(name, email);
		if (existMember != null) return ResultData.from("F-10", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다.", name, email));
		
		existMember = memberRepository.getMemberByNameAndCellphoneNum(name, cellphoneNum);
		if (existMember != null) return ResultData.from("F-11", Ut.f("이미 사용중인 이름(%s)과 전화번호(%s)입니다.", name, cellphoneNum));
		
		
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		int id = memberRepository.getLastInsertId();
		
		return ResultData.from("S-1", "회원가입 성공", id);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

//	public int doLogin(String loginId, String loginPw) {
//		
//		if (memberRepository.doLogin(loginId, loginPw) == 0) return 0;
//		
//		
//		return memberRepository.getMemberByLoginId1(loginId);
//	}
//	
//	public String getNickname(int id) {
//		
//		return memberRepository.getNickname(id);
//	}

}