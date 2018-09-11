package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/11 13:49
 */
@Getter
public enum ActivateEnum {

  ENABLED(0,"激活"),
  DISABLED(1,"未激活");

  private Integer code;
  private String name;

  ActivateEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
