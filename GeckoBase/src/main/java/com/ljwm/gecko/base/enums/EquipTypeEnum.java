package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

@Getter
public enum  EquipTypeEnum implements CommonEnum {

  PC(0,"PC端"),
  WEIXIN(1,"微信小程序端");

  private Integer code;
  private String name;

  EquipTypeEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
