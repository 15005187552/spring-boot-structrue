package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/11 16:15
 */
@Getter
public enum PersonStateEnum implements CommonEnum {
  NOMAl(0, "正常");

  private Integer code;
  private String name;

  PersonStateEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
