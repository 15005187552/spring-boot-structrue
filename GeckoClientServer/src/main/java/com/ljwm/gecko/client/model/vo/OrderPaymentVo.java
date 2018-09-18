package com.ljwm.gecko.client.model.vo;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Created by yunqisong on 2018/3/21/021.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderPaymentVo extends PaymentBaseVo {

  @ApiModelProperty("订单ID")
  private Long id;

  public OrderPaymentVo(Long id, Map map) {
    BeanUtil.fillBeanWithMap(map, this, true);
    this.setPaySign((String) map.get("sign"));          // 前台要求转化为PaySign
    this.setPackageName((String) map.get("package")); // package为java关键字手动设置值
    this.setId(id);
  }
}
