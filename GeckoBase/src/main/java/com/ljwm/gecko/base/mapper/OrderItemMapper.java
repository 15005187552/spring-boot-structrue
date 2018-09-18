package com.ljwm.gecko.base.mapper;

import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.OrderItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Livis
 * @since 2018-09-18
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {
  OrderItem findByOrderItemNo(@Param("orderItemNo") String orderItemNo);

  List<OrderItemVo> findProviderOrderItemVoList(@Param("params") Kv kv);

  List<OrderItemVo> findOrderItemVoList(@Param("orderNo") String orderNo);
}
