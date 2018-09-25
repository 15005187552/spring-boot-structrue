package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderVo extends Order {

  private List<OrderItemVo> orderItemVoList;

  public OrderVo(Order order){
    if (order!=null){
      BeanUtil.copyProperties(order,this);
    }
  }
}
