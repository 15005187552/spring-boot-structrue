package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-10
 */
public interface OrderMapper extends BaseMapper<Order> {
  List<OrderVo> findProviderPage(Page<OrderVo> page, @Param("params")Kv kv);

  List<OrderVo> findPage(Page<OrderVo> page, @Param("params")Map map);

  @Select("SELECT IF((SELECT COUNT(`id`) FROM `t_order_item` WHERE `order_no`=#{orderNo})=1, `service_name` ,CONCAT(`service_name`,'等')) AS res FROM `t_order_item`  WHERE `order_no` = #{orderNo} LIMIT 0, 1   ")
  String getOrderInfo(String orderNo);

  Integer findProviderOrderCount(@Param("providerId") Long providerId);
}
