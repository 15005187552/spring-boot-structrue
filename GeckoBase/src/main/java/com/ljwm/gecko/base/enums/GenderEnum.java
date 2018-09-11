package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/11 15:09
 */
@Getter
public enum GenderEnum implements CommonEnum {
  MAN(0,"男"),
  WOMEN(1,"女");

  private Integer code;
  private String name;

  GenderEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
