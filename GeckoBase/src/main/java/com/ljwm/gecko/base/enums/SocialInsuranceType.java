package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/8/23 18:43
 */
@Getter
public enum SocialInsuranceType implements CommonEnum {
  COMPANY(0, "公司代缴"),
  PERSONAL(1, "个人缴纳")
  ;

  private Integer code;
  private String name;

  SocialInsuranceType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

}
