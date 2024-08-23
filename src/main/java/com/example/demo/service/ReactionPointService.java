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

	public int toggleReactionPoint(int loginedMemberId, String relTypeCode, int relId, int newPoint) {
		
	    ReactionPoint existingReaction = reactionPointRepository.getReactionPointByMemberIdAndRelId(loginedMemberId, relTypeCode, relId);
	    
	    System.err.println(existingReaction);

	    if (existingReaction == null) {
	        // 현재 반응이 없을 경우 -> 새로운 반응을 추가
	        reactionPointRepository.insertReactionPoint(loginedMemberId, relTypeCode, relId, newPoint);
	        return newPoint;
	    } 
	    
	    if (existingReaction.getPoint() == 1 && newPoint == 0) {
	        // 현재 좋아요인 경우 -> 좋아요를 취소
	        reactionPointRepository.deleteReactionPoint(loginedMemberId, relTypeCode, relId);
	        return 0;
	    } 
	    
	    if (existingReaction.getPoint() == -1 && newPoint == 0) {
	        // 현재 싫어요인 경우 -> 싫어요를 취소
	        reactionPointRepository.deleteReactionPoint(loginedMemberId, relTypeCode, relId);
	        return 0;
	    }
	    
	    if (existingReaction.getPoint() == 1 && newPoint == -1) {
	        // 현재 좋아요인 경우 -> 싫어요로 변경
	        reactionPointRepository.updateReactionPoint(loginedMemberId, relTypeCode, relId, -1);
	        return -1;
	    }
	    
	    if (existingReaction.getPoint() == -1 && newPoint == 1) {
	        // 현재 싫어요인 경우 -> 좋아요로 변경
	        reactionPointRepository.updateReactionPoint(loginedMemberId, relTypeCode, relId, 1);
	        return 1;
	    }
	    
	    // 동일한 반응을 눌렀을 경우 취소
	    reactionPointRepository.deleteReactionPoint(loginedMemberId, relTypeCode, relId);
	    return 0;
	}

	public int getTotalReactionPoints(String relTypeCode, int relId) {
		Integer totalPoints = reactionPointRepository.getTotalReactionPoints(relTypeCode, relId);
		return totalPoints != null ? totalPoints : 0;
	}

	public void updateArticleReactionPoints() {
        reactionPointRepository.updateArticleReactionPoints();
    }
}
