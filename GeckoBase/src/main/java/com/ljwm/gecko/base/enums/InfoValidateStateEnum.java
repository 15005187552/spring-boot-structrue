package com.ljwm.gecko.base.enums;

import org.apache.commons.lang3.StringUtils;

public enum InfoValidateStateEnum {

  INIT(0, "初始化"),
  CONFIRM_SUCCESS(1, "审核通过"),
  CONFIRM_FAILED(2, "审核失败");

  private Integer code;
  private String name;

  InfoValidateStateEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public Integer getCode() {
    return code;
  }

  public String getName() {
    return name;
  }


  public static String getName(Integer code) {
    for (InfoValidateStateEnum infoValidateStateEnum : values()) {
      if (infoValidateStateEnum.getCode().intValue() == code.intValue()) {
        return infoValidateStateEnum.getName();
      }
    }
    return StringUtils.EMPTY;
  }
}
