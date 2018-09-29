package com.ljwm.gecko.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatSessionEnum {

  ACTIVE(0, "激活"),
  INACTIVE(1, "未激活");

  private Integer code;

  private String info;
}
