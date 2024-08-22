package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ReactionPointRepository;
import com.example.demo.vo.ReactionPoint;

@Service
public class ReactionPointService {
	@Autowired
	private ReactionPointRepository reactionPointRepository;

	public int toggleReactionPoint(int memberId, String relTypeCode, int relId) {
		ReactionPoint reactionPoint = reactionPointRepository.getReactionPointByMemberIdAndRelId(memberId, relTypeCode,
				relId);

		if (reactionPoint != null) {
			reactionPointRepository.deleteReactionPoint(memberId, relTypeCode, relId);
			return -1; // 기존 좋아요 또는 싫어요를 취소함
		} else {
			reactionPointRepository.insertReactionPoint(memberId, relTypeCode, relId, 1);
			return 1; // 새로 좋아요를 누름
		}
	}

	public int getTotalReactionPoints(String relTypeCode, int relId) {
		Integer totalPoints = reactionPointRepository.getTotalReactionPoints(relTypeCode, relId);
		return totalPoints != null ? totalPoints : 0;
	}
}
