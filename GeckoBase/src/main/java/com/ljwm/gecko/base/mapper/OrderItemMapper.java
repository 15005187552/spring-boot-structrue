package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.OrderItem;
import com.ljwm.gecko.base.model.vo.OrderItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

  List<OrderItemVo> findOrderItemList(Page<OrderItemVo> ret, @Param("params") Map params);
}
