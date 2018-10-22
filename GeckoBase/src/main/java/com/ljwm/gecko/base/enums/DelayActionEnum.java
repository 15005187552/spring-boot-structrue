package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

import java.util.concurrent.Delayed;

/**
 * @author Janiffy
 * @date 2018/10/19 13:42
 */
@Getter
public enum DelayActionEnum implements CommonEnum{
  WWW(1, null, null, null);

  private Integer code;

  private String key;

  private String name;

  private Class<? extends Delayed> invokeClass;

  DelayActionEnum(Integer code, String key, String name, Class<? extends Delayed> invokeClass) {
    this.code = code;
    this.key = key;
    this.name = name;
    this.invokeClass = invokeClass;
  }
}
