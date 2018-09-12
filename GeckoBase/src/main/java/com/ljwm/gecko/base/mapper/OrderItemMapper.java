package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-11
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {
  OrderItem findByOrderItemNo(@Param("orderItemNo") String orderItemNo);
}
