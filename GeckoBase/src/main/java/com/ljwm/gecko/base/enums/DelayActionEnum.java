package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.delay.DelayEvent;
import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

import java.util.concurrent.Delayed;

/**
 * @author Janiffy
 * @date 2018/10/19 13:42
 */
@Getter
public enum DelayActionEnum implements CommonEnum{
  REMIND_AN_HOUR(0, "remind", "报税数据确认剩余一小时提醒", DelayEvent.class);

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
