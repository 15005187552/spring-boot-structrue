package com.ljwm.gecko.base.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public enum ProviderTypeEnum {

  COMPANY(1, "公司"),
  PERSON(0, "个人");

  private Integer code;

  private String name;

  ProviderTypeEnum(Integer code,String name){
    this.code = code;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Integer getCode() {
    return code;
  }

  public static String codeOf(Integer code){
    for(ProviderTypeEnum providerTypeEnum : values()){
      if(Objects.equals(providerTypeEnum.getCode(),code)){
        return providerTypeEnum.getName();
      }
    }
    return StringUtils.EMPTY;
  }
}
