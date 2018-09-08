package com.ljwm.gecko.base.enums;

import org.apache.commons.lang3.StringUtils;

public enum ProviderStatEnum {

  WAIT_CONFIRM(1, "待审核"),
  CONFIRM_SUCCESS(2, "审核通过"),
  CONFIRM_FAILED(3, "审核失败");

  private Integer code;
  private String name;

  ProviderStatEnum(Integer code, String name) {
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
    for (ProviderStatEnum providerStatEnum : values()) {
      if (providerStatEnum.getCode().intValue() == code.intValue()) {
        return providerStatEnum.getName();
      }
    }
    return StringUtils.EMPTY;
  }
}
