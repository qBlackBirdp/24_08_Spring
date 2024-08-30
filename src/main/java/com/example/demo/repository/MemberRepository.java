package com.example.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.Member;

@Mapper
public interface MemberRepository {

	@Select("SELECT LAST_INSERT_ID();")
	public int getLastInsertId();

	@Insert("INSERT INTO `member` SET regDate = NOW(), updateDate = NOW(), loginId = #{loginId}, loginPw = #{loginPw}, `name` = #{name}, nickname = #{nickname}, cellphoneNum = #{cellphoneNum}, email = #{email}")
	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	@Select("SELECT * FROM `member` WHERE id = #{id}")
	public Member getMemberById(int id);
	
	@Select("SELECT * FROM `member` WHERE loginId = #{loginId}")
	public Member getMemberByLoginId(String loginId);
	
	@Select("SELECT * FROM `member` WHERE nickname = #{nickname}")
	public Member getMemberByNickname(String nickname);
	
	@Select("SELECT * FROM `member` WHERE email = #{email}")
	public Member getMemberByEmail(String email);
	
	@Select("SELECT * FROM `member` WHERE email = #{email} AND `name` = #{name}")
	public Member getMemberByNameAndEmail(String name, String email);
	
	@Select("SELECT * FROM `member` WHERE cellphoneNum = #{cellphoneNum} AND `name` = #{name}")
	public Member getMemberByNameAndCellphoneNum(String name, String cellphoneNum);
	
	@Select("SELECT COUNT(*) FROM `member` WHERE loginId = #{loginId} AND loginPw = #{loginPw}")
	public int doLogin(String loginId, String loginPw);
	
	@Select("SELECT nickname FROM `member` WHERE id = #{id}")
	public String getNickname(int id);
//	
//	@Select("SELECT * FROM `member` WHERE loginId = #{loginId}")
//	public int getMemberByLoginId1(String loginId);
	
	@Update("""
			<script>
				UPDATE `member`
				<set>
					<if test="loginPw != null">
						loginPw = #{loginPw},
					</if>
					<if test="name != null">
						name = #{name},
					</if>
					<if test="nickname != null">
						nickname = #{nickname},
					</if>
					<if test="cellphoneNum != null">
						cellphoneNum = #{cellphoneNum},
					</if>
					<if test="email != null">
						email = #{email},
					</if>
					updateDate = NOW()
				</set>
				WHERE id = #{loginedMemberId}
			</script>
			""")
	public void modify(int loginedMemberId, String loginPw, String name, String nickname, String cellphoneNum,
			String email);

	@Update("""
			<script>
				UPDATE `member`
				<set>
					<if test="name != null">
						name = #{name},
					</if>
					<if test="nickname != null">
						nickname = #{nickname},
					</if>
					<if test="cellphoneNum != null">
						cellphoneNum = #{cellphoneNum},
					</if>
					<if test="email != null">
						email = #{email},
					</if>
					updateDate = NOW()
				</set>
				WHERE id = #{loginedMemberId}
			</script>
			""")
	public void modifyWithoutPw(int loginedMemberId, String name, String nickname, String cellphoneNum, String email);

}