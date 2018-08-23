package com.ljwm.gecko.base.enums;

/**
 * @author Janiffy
 * @date 2018/8/23 17:58
 */
public enum CertificateType {
  IDENTITY_CARD(0, "身份证")
  ;

  private Integer code;
  private String name;

  CertificateType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static CertificateType codeOf(Integer code) {
    for(CertificateType certificateType : CertificateType.values()) {
      if (certificateType.code.equals(code)) {
        return certificateType;
      }
    }
    return null;
  }

  public static CertificateType codeOf(String code) {
    return codeOf(Integer.valueOf(code));
  }
}
