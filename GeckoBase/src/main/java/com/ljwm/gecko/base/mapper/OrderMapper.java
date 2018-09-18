package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

  List<OrderVo> findPage(Page<OrderVo> page, @Param("params")Kv kv);
}
