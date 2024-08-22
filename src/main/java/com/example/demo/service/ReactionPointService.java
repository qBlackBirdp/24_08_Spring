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

    public List<ReactionPoint> getReactionPoints(String relTypeCode, int relId) {
        return reactionPointRepository.getReactionPoints(relTypeCode, relId);
    }

    public int getTotalReactionPoints(String relTypeCode, int relId) {
        return reactionPointRepository.getTotalReactionPoints(relTypeCode, relId);
    }
}
