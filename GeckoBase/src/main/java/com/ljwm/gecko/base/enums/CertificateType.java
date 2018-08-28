package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/8/23 17:58
 */
@Getter
public enum CertificateType implements CommonEnum {
  IDENTITY_CARD(0, "身份证")
  ;

  private Integer code;
  private String name;

  CertificateType(Integer code, String name) {
  }
}
