package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.model.vo.WxResultMe;
import org.apache.ibatis.annotations.Param;
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

  @Select("SELECT a.NICK_NAME, b.USERNAME, b.EXT_INFO FROM t_member a, t_member_account b WHERE a.ID = b.MEMBER_ID AND b.USERNAME = #{userName}")
  WxResultMe selectByUserName(String userName);

  List<MemberVo> find(Page<MemberVo> page, @Param("params")Map map);

  @Select("SELECT * FROM t_member a, t_member_account b, t_member_password c WHERE a.ID=#{memberId}  \n" +
    "AND b.TYPE = #{code}\n" +
    "AND b.MEMBER_ID =#{memberId}\n" +
    "AND c.ID = (SELECT b.PASSWORD_ID FROM t_member_account b WHERE b.MEMBER_ID =#{memberId} AND b.TYPE= #{code})\t")
  MemberVo selectByMeVo(Long memberId, Integer code);
}
