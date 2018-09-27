package com.ljwm.gecko.base.enums;

import org.apache.commons.lang3.StringUtils;

public enum ProviderRoleEnum {
  CREATOR(1, "创建人"),
  PREPARER(2, "报税员"),
  OPERATOR(3, "话务员");

  private Integer code;
  private String name;

  ProviderRoleEnum(Integer code, String name) {
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
    for (ProviderRoleEnum providerRoleEnum : values()) {
      if (providerRoleEnum.getCode().intValue() == code.intValue()) {
        return providerRoleEnum.getName();
      }
    }
    return StringUtils.EMPTY;
  }
}
