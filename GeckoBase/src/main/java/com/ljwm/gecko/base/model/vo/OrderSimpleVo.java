package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderSimpleVo extends Order {

  public OrderSimpleVo(Order order){
    if (order!=null){
      BeanUtil.copyProperties(order,this);
    }
  }
}
