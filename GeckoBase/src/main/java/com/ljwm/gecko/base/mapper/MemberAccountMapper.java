package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.MemberAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.MemberInfo;
import com.ljwm.gecko.base.model.vo.MemberVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.websocket.server.ServerEndpoint;
import java.util.List;

/**
 * <p>
 * 会员账号表 Mapper 接口
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-22
 */
@Repository
public interface MemberAccountMapper extends BaseMapper<MemberAccount> {


  @Select("SELECT * FROM `t_member_account` WHERE `MEMBER_ID` = #{id}")
  @ResultMap("BaseResultMap")
  List<MemberAccount> findByMember(@Param("ID") Long id);

}
