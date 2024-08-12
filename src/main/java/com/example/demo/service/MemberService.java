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

		Member existMemeber = memberRepository.getMemberByLoginId(loginId);
		if (existMemeber != null) return -1;
		
		existMemeber = memberRepository.getMemberByNickname(nickname);
		if (existMemeber != null) return -2;
		
		existMemeber = memberRepository.getMemberByEmail(email);
		if (existMemeber != null) return -3;
		

		existMemeber = memberRepository.getMemberByNameAndEmail(name, email);
		if (existMemeber != null) return -4;
		
		existMemeber = memberRepository.getMemberByNameAndCellphoneNum(name, cellphoneNum);
		if (existMemeber != null) return -5;

		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		return memberRepository.getLastInsertId();
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}