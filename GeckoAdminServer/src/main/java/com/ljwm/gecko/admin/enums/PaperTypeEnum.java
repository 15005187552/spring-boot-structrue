package com.ljwm.gecko.admin.enums;

import lombok.Getter;

@Getter
public enum  PaperTypeEnum {

  PERSON(0,"个人"),
  COMPANY(1,"企业");

  private Integer code;
  private String name;

  PaperTypeEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
