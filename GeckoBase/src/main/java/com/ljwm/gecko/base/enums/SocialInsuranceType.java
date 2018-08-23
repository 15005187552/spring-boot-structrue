package com.ljwm.gecko.base.enums;

/**
 * @author Janiffy
 * @date 2018/8/23 18:43
 */
public enum SocialInsuranceType {
  COMPANY(0, "公司代缴"),
  PERSONAL(1, "个人缴纳")
  ;

  private Integer code;
  private String name;

  SocialInsuranceType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static SocialInsuranceType codeOf(Integer code) {
    for(SocialInsuranceType socialInsuranceType : SocialInsuranceType.values()) {
      if (socialInsuranceType.code.equals(code)) {
        return socialInsuranceType;
      }
    }
    return null;
  }

  public static SocialInsuranceType codeOf(String code) {
    return codeOf(Integer.valueOf(code));
  }
}
