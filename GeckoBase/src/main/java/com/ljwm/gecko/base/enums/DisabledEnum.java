package com.ljwm.gecko.base.enums;

import lombok.Getter;

@Getter
public enum DisabledEnum {

  ENABLED(0,"启用",true),
  DISABLED(1,"禁用",false);

  private Integer code;
  private String name;
  private Boolean info;

  DisabledEnum(Integer code, String name,Boolean info) {
    this.code = code;
    this.name = name;
    this.info = info;
  }

}
