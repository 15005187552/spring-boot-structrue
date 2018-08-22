package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * 用户来源
 * Created by yuzhou on 2018/8/21.
 */
@Getter
public enum UserSource {
  PC_WEB(0, "PC端网页"),
  WX_APP(1, "微信小程序"),
  ANDROID_APP(2, "Android App"),
  IOS_APP(3, "iOS APP")
  ;

  private Integer code;
  private String name;

  UserSource(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static UserSource codeOf(Integer code) {
    for(UserSource userSource : UserSource.values()) {
      if (userSource.code.equals(code)) {
        return userSource;
      }
    }
    return null;
  }
}
