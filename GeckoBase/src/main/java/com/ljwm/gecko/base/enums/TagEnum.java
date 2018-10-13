package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: xixil
 * Date: 2018/10/12 17:16
 * RUA
 */

@Getter
@AllArgsConstructor
public enum TagEnum implements CommonEnum {

  NEWEST(0,"最新"),
  HEADLINE(1,"头条");

  private Integer code;

  private String name;
}
