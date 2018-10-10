package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/10/10 11:40
 */
@Getter
public enum TaxStatus implements CommonEnum {
  NEED_CONFIRM(0, "待确认"),
  CONFIRMED(1, "已确认");

  private Integer code;

  private String name;

  TaxStatus(Integer code, String name) {
    this.code = code;
    this.name = name;
  }
}
