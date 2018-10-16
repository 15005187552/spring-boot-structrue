package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.Order;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderVo extends Order {

  @JSONField(serializeUsing = StatusWithNameSerializer.OrderStatusSerializer.class)
  private Integer status;

  private List<OrderItemVo> orderItemVoList;

  public OrderVo(Order order){
    if (order!=null){
      BeanUtil.copyProperties(order,this);
    }
  }
}
