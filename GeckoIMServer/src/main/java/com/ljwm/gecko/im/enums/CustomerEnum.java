package com.ljwm.gecko.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: xixil
 * Date: 2018/10/10 11:34
 * RUA
 */

@Getter
@AllArgsConstructor
public enum CustomerEnum {

  MEMBER(0,"会员"),
  ADMIN(1,"后台用户"),
  GUEST(2,"游客");

  private Integer code;

  private String info;
}
