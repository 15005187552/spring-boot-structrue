package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * 企业类型
 * Created by yuzhou on 2018/8/21.
 */
@Getter
public enum CompanyType {
  COMPANY(0, "企业"),
  PUBLIC(1, "事业单位"),
  SOCIAL(2, "社会团体")
  ;

  private Integer code;
  private String name;

  CompanyType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static CompanyType codeOf(Integer code) {
    for(CompanyType loginType : CompanyType.values()) {
      if (loginType.code.equals(code)) {
        return loginType;
      }
    }
    return null;
  }

  public static CompanyType codeOf(String code) {
    return codeOf(Integer.valueOf(code));
  }
}
