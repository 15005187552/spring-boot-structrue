package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.Order;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderSimpleVo extends Order {

  public OrderSimpleVo(Order order){
    if (order!=null){
      BeanUtil.copyProperties(order,this);
    }
  }
}
