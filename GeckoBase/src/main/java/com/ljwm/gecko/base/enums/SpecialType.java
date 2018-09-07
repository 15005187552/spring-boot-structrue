package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/7 10:48
 */
@Getter
public enum SpecialType {

  PERCENT(0, "百分比"),
  MONEY(1, "金额"),
  ;

  private Integer code;
  private String name;

  SpecialType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
