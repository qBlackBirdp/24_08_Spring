package com.example.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.ReactionPoint;

@Mapper
public interface ReactionPointRepository {

    @Select("SELECT * FROM reactionPoint WHERE memberId = #{memberId} AND relTypeCode = #{relTypeCode} AND relId = #{relId}")
    ReactionPoint getReactionPointByMemberIdAndRelId(int memberId, String relTypeCode, int relId);
    
    @Insert("INSERT INTO reactionPoint (regDate, updateDate, memberId, relTypeCode, relId, point) VALUES (NOW(), NOW(), #{memberId}, #{relTypeCode}, #{relId}, #{point})")
    void insertReactionPoint(int memberId, String relTypeCode, int relId, int point);

    @Delete("DELETE FROM reactionPoint WHERE memberId = #{memberId} AND relTypeCode = #{relTypeCode} AND relId = #{relId}")
    void deleteReactionPoint(int memberId, String relTypeCode, int relId);
    
    @Update("UPDATE reactionPoint SET point = #{point}, updateDate = NOW() WHERE memberId = #{memberId} AND relTypeCode = #{relTypeCode} AND relId = #{relId}")
    void updateReactionPoint(int memberId, String relTypeCode, int relId, int point);

    @Select("SELECT SUM(point) FROM reactionPoint WHERE relTypeCode = #{relTypeCode} AND relId = #{relId}")
    Integer getTotalReactionPoints(String relTypeCode, int relId);

    @Update("""
            UPDATE article AS A
            INNER JOIN (
                SELECT RP.relTypeCode, RP.relId,
                SUM(IF(RP.point > 0, RP.point, 0)) AS goodReactionPoint,
                SUM(IF(RP.point < 0, RP.point * -1, 0)) AS badReactionPoint
                FROM reactionPoint AS RP
                GROUP BY RP.relTypeCode, RP.relId
            ) AS RP_SUM
            ON A.id = RP_SUM.relId
            SET A.goodReactionPoint = RP_SUM.goodReactionPoint,
            A.badReactionPoint = RP_SUM.badReactionPoint;
        """)
	void updateArticleReactionPoints();
}
