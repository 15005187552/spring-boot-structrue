package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/11 16:19
 */
@Getter
public enum YesOrNoEnum implements CommonEnum {
  YES(0, "是"),
  NO(1, "否");

  private Integer code;

  private String name;

  YesOrNoEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
