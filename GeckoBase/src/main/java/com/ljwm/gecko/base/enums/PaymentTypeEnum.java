package com.ljwm.gecko.base.enums;

import io.swagger.models.auth.In;

import java.util.Objects;

public enum PaymentTypeEnum {
  ONLINE_PAY(1,"在线支付");

  PaymentTypeEnum(Integer code,String value){
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
    for(PaymentTypeEnum paymentTypeEnum : values()){
      if(Objects.equals(paymentTypeEnum.getCode(),code)){
        return paymentTypeEnum.getValue();
      }
    }
    throw new RuntimeException("么有找到对应的枚举");
  }

}
