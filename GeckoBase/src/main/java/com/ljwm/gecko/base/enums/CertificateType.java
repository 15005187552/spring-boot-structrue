package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/8/23 17:58
 */
@Getter
public enum CertificateType implements CommonEnum {
  IDENTITY_CARD(0, "居民身份证"),
  MILITARY_OFFICER(1, "军官证"),
  MILITARY_NOMAL(2, "士兵证"),
  POLICE(3, "武警警官证"),
  CHINA_PASS(4, "中国护照")

  ;

  private Integer code;
  private String name;

  CertificateType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

}
