package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/4 11:01
 */
@Getter
public enum IdentificationType {
  //0：未认证，1：认证通过，2:认证失败
  NO_IDENTI(0, "未认证"),
  PASS_IDENTI(0, "认证通过"),
  FAIL_IDENTI(0, "认证失败");

  private Integer code;
  private String name;

  IdentificationType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }


}
