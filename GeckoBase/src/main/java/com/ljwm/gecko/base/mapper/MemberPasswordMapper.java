package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.MemberPassword;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.LoginVo;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 账号密码表 Mapper 接口
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-22
 */
@Repository
public interface MemberPasswordMapper extends BaseMapper<MemberPassword> {

  @Select("SELECT b.SALT, b.`PASSWORD`, a.MEMBER_ID FROM t_member_account a, t_member_password b\n" +
    "WHERE a.USERNAME = #{phoneNum} AND b.ID = a.PASSWORD_ID")
  @ResultMap("BaseMap")
  List<LoginVo> selectByPhone(String phoneNum);
}
