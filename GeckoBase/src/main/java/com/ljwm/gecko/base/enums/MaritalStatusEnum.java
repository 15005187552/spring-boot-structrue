package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/11 16:42
 */
@Getter
public enum MaritalStatusEnum implements CommonEnum {
  UNMARRIGED(0, "未婚"),
  MARRIGED(1, "已婚"),
  WIDOWED(2, "丧偶");

  private Integer code;

  private String name;

  MaritalStatusEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
