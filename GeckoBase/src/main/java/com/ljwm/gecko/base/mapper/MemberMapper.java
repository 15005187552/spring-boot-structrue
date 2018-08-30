package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MobileCode;
import com.ljwm.gecko.base.model.vo.WxResultMe;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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

  MobileCode selectByPhone(String phoneNum);
}
