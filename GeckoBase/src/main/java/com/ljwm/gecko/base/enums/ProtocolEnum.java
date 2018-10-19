package com.ljwm.gecko.base.enums;

/**
 * @author Janiffy
 * @date 2018/10/19 10:05
 */

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

@Getter
public enum ProtocolEnum implements CommonEnum {
  REGISTER(0, "注册许可协议");

  private Integer code;

  private String name;

  ProtocolEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
