package com.ljwm.gecko.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: xixil
 * Date: 2018/10/18 15:23
 * RUA
 */
@Getter
@AllArgsConstructor
public enum IsTopEnum {

  NOT_TOP(0,"未置顶"),
  TOP(1,"置顶")
  ;
  private Integer code;
  private String info;
}
