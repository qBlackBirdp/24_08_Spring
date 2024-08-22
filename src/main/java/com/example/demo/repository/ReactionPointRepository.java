package com.example.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.ReactionPoint;

@Mapper
public interface ReactionPointRepository {

    @Select("SELECT * FROM reactionPoint WHERE memberId = #{memberId} AND relTypeCode = #{relTypeCode} AND relId = #{relId}")
    ReactionPoint getReactionPointByMemberIdAndRelId(int memberId, String relTypeCode, int relId);

    @Insert("INSERT INTO reactionPoint (regDate, updateDate, memberId, relTypeCode, relId, point) VALUES (NOW(), NOW(), #{memberId}, #{relTypeCode}, #{relId}, #{point})")
    void insertReactionPoint(int memberId, String relTypeCode, int relId, int point);

    @Delete("DELETE FROM reactionPoint WHERE memberId = #{memberId} AND relTypeCode = #{relTypeCode} AND relId = #{relId}")
    void deleteReactionPoint(int memberId, String relTypeCode, int relId);

    @Select("SELECT SUM(point) FROM reactionPoint WHERE relTypeCode = #{relTypeCode} AND relId = #{relId}")
    Integer getTotalReactionPoints(String relTypeCode, int relId);
}
