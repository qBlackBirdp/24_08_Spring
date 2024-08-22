package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.ReactionPoint;

@Mapper
public interface ReactionPointRepository {

	@Select("SELECT * FROM reactionPoint WHERE relTypeCode = #{relTypeCode} AND relId = #{relId}")
    List<ReactionPoint> getReactionPoints(String relTypeCode, int relId);

    @Select("SELECT SUM(point) FROM reactionPoint WHERE relTypeCode = #{relTypeCode} AND relId = #{relId}")
    int getTotalReactionPoints(String relTypeCode, int relId);

}
