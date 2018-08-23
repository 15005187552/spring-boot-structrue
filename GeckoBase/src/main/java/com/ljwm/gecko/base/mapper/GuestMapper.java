package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.Guest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 游客表 Mapper 接口
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-22
 */
@Repository
public interface GuestMapper extends BaseMapper<Guest> {

  @Select("SELECT * FROM `t_guest` WHERE `GUEST_ID`=#{guestId}")
  @ResultMap("BaseResultMap")
  Guest findByGuestId(String guestId);
}
