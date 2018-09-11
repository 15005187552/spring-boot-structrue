package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/11 16:08
 */
@Getter
public enum EducationEnum implements CommonEnum {

  JUNIOR(0, "专科"),
  REGULAR(1, "本科"),
  MASTER(2, "硕士"),
  DOCTOR(3, "博士");

  private Integer code;
  private String name;

  EducationEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

}
