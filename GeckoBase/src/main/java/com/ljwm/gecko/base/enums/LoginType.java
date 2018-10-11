package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * 登录类型
 * Created by yuzhou on 2018/8/21.
 */
@Getter
public enum LoginType {
  GUEST(-1, "游客"),
  MOBILE(0, "手机号"),
  WX_APP(1, "微信小程序"),
  ADMIN(2,"后台"),
  PROVIDER(3,"服务商")
  ;

  private Integer code;
  private String name;

  LoginType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static LoginType codeOf(Integer code) {
    for(LoginType loginType : LoginType.values()) {
      if (loginType.code.equals(code)) {
        return loginType;
      }
    }
    return null;
  }

  public static LoginType codeOf(String code) {
    return codeOf(Integer.valueOf(code));
  }
}
