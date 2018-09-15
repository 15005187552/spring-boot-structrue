package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.model.vo.MemberVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-22
 */
@Repository
public interface MemberMapper extends BaseMapper<Member> {

  @Select("SELECT * FROM t_member a, t_member_account b, t_member_password c WHERE b.USERNAME = #{userName} AND b.MEMBER_ID = a.ID AND b.PASSWORD_ID =c.ID")
  @ResultMap("BaseMap")
  MemberVo selectByUserName(String userName);

  List<MemberVo> find(Page<MemberVo> page, @Param("params")Map map);

  @Select("SELECT * FROM t_member a, t_member_account b, t_member_password c WHERE a.ID=#{memberId}\n" +
    "AND b.TYPE = #{code} \n" +
    "AND b.MEMBER_ID = #{memberId}\n" +
    "AND c.ID = (SELECT b.PASSWORD_ID FROM t_member_account b WHERE b.MEMBER_ID =#{memberId} AND b.TYPE= #{code})")
  @ResultMap("BaseMap")
  List<MemberVo> selectByMeVoAndCode(@Param("memberId") Long memberId, @Param("code")Integer code);


  @Select("SELECT * FROM t_member a, t_member_account b, t_member_password c WHERE b.TYPE=#{type} AND b.MEMBER_ID = a.ID AND a.ID=#{memberId} AND b.PASSWORD_ID =c.ID")
  @ResultMap("BaseMap")
  List<MemberVo> selectByMeVoAndType(@Param("memberId")Long memberId, @Param("type")String type);

  MemberVo findMemberVoByPhone(@Param("regMobile") String regMobile);

  List<MemberVo> findByPage(Page<MemberVo> ret, @Param("params") Kv params);

  MemberVo findMemberVoByMemberId(@Param("memberId") Long memberId);
}
