package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * 登录类型
 * Created by yuzhou on 2018/8/21.
 */
@Getter
public enum CompanyType {
  GUEST(-1, "游客"),
  MOBILE(0, "手机号"),
  WX_APP(1, "微信小程序")
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
