package com.ljwm.gecko.admin.enums;

import lombok.Getter;

@Getter
public enum  CompanyValidateEnum {

  UNVALIDATE(0,"未认证"),
  PASSVALIDATE(1,"认证通过"),
  REJECTVALIDATE(1,"认证失败");

  private Integer code;
  private String name;

  CompanyValidateEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
