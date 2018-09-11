package com.ljwm.gecko.base.enums;

import org.apache.commons.lang3.StringUtils;

public enum ProviderCustomStatusEnum {
  ENABLED(1,"在售",true),
  DISABLED(2,"下架",false),
  DELETE(3,"删除",false);

  private Integer code;
  private String name;
  private Boolean info;

  ProviderCustomStatusEnum(Integer code, String name,Boolean info) {
    this.code = code;
    this.name = name;
    this.info = info;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static String getName(Integer code) {
    for (ProviderCustomStatusEnum providerCustomStatusEnum : values()) {
      if (providerCustomStatusEnum.getCode().intValue() == code.intValue()) {
        return providerCustomStatusEnum.getName();
      }
    }
    return StringUtils.EMPTY;
  }
}
