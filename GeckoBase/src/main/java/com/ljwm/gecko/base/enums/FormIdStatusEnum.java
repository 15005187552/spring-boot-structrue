package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/20 14:38
 */
@Getter
public enum FormIdStatusEnum implements CommonEnum {
  USE_ABLE(0, "可使用"),
  USE_LESS(1, "不可使用");

  private Integer code;

  private String name;

  FormIdStatusEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

}
