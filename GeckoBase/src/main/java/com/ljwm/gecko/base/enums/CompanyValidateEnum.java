package com.ljwm.gecko.base.enums;

import lombok.Getter;

@Getter
public enum CompanyValidateEnum {

  UN_VALIDATE(0,"未认证"),
  PASS_VALIDATE(1,"认证通过"),
  REJECT_VALIDATE(1,"认证失败");

  private Integer code;
  private String name;

  CompanyValidateEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
