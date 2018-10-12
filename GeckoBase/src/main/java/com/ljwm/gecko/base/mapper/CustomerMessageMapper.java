package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.CustomerMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 客服消息表 Mapper 接口
 * </p>
 *
 * @author YunQiSong
 * @since 2018-09-24
 */
@Repository
public interface CustomerMessageMapper extends BaseMapper<CustomerMessage> {


  @Select("SELECT * FROM `im_customer_message` WHERE `CUSTOMER_SESSION_ID` = #{sessionId}")
  List<CustomerMessage> getCustomerMessages(@Param("sessionId") Long sessionId);
}
