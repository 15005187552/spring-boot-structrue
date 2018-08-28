package com.ljwm.gecko.admin.enums;

import lombok.Getter;

@Getter
public enum  DeleteEnum {

  NORMAL(0,"普通删除",false),
  FORCE(1,"强制删除",true);

  private Integer code;
  private String name;
  private Boolean info;

  DeleteEnum(Integer code, String name,Boolean info) {
    this.code = code;
    this.name = name;
    this.info = info;
  }
}
