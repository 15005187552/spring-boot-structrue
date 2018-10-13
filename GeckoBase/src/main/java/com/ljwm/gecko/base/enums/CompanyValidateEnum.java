package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CompanyValidateEnum implements CommonEnum {

  UN_VALIDATE(0,"未认证"),
  PASS_VALIDATE(1,"认证通过"),
  REJECT_VALIDATE(2,"认证失败");

  private Integer code;
  private String name;

  CompanyValidateEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static String getName(Integer code) {
    for (CompanyValidateEnum validateStatEnum : values()) {
      if (validateStatEnum.getCode().intValue() == code.intValue()) {
        return validateStatEnum.getName();
      }
    }
    return StringUtils.EMPTY;
  }
}
