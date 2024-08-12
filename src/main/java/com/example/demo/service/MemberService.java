package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.vo.Member;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
//		if (getMemberByLoginId(loginId) != null) {
//			throw new IllegalArgumentException("이미 사용 중인 로그인 아이디입니다.");
//		}
//
//		if (getMemberByNickname(nickname) != null) {
//			throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
//		}
//
//		if (getMemberByEmail(email) != null) {
//			throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
//		}
		
		Member existMemeber = memberRepository.getMemberByLoginId(loginId);
		if (existMemeber != null) {
			return -1;
		}
		existMemeber = memberRepository.getMemberByNickname(nickname);
		if (existMemeber != null) {
			return -2;
		}
		existMemeber = memberRepository.getMemberByEmail(email);
		if (existMemeber != null) {
			return -3;
		}
		
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		return memberRepository.getLastInsertId();
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}