package com.ljwm.gecko.base.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
public enum OrderStatusEnum {
  CANCELED(0,"已取消"),
  WAIT(5,"待服务定价"),
  //OVER_CONFIRM(8,"已定价"),
  NO_PAID(10,"首付款未支付"),
  NO_REMAIN_PAID(15,"尾款未支付"),
  PAID(20,"已付款"),
  ORDER_SUCCESS(50,"交易成功"),
  ORDER_CLOSE(60,"交易关闭");

  OrderStatusEnum(Integer code,String value){
    this.code = code;
    this.value = value;
  }
  private String value;
  private Integer code;

  public String getValue() {
    return value;
  }

  public Integer getCode() {
    return code;
  }

  public static String codeOf(Integer code){
    for(OrderStatusEnum orderStatusEnum : values()){
      if(Objects.equals(orderStatusEnum.getCode(),code)){
        return orderStatusEnum.getValue();
      }
    }
    return StringUtils.EMPTY;
  }
}
