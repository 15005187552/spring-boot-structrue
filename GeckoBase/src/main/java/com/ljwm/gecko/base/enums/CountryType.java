package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/8/23 17:40
 */
@Getter
public enum CountryType {
  CHINA(0, "中国大陆")/*,
  HTMT(2, "港澳台"),
  FOREIGN(3, "外籍")*/
  ;

  private Integer code;
  private String name;

  CountryType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static CountryType codeOf(Integer code) {
    for(CountryType countryType : CountryType.values()) {
      if (countryType.code.equals(code)) {
        return countryType;
      }
    }
    return null;
  }

  public static CountryType codeOf(String code) {
    return codeOf(Integer.valueOf(code));
  }
}
