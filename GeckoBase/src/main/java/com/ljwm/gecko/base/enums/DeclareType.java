package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/8/23 19:43
 */
@Getter
public enum DeclareType implements CommonEnum {
  MONTH(0, "月报"),
  YEAR(1, "年报")
  ;

  private Integer code;
  private String name;

  DeclareType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

}
