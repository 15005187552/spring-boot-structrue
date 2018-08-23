package com.ljwm.gecko.base.enums;

/**
 * @author Janiffy
 * @date 2018/8/23 19:43
 */
public enum DeclareType {
  MONTH(0, "月报"),
  YEAR(1, "年报")
  ;
  ;

  private Integer code;
  private String name;

  DeclareType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static DeclareType codeOf(Integer code) {
    for(DeclareType declareType : DeclareType.values()) {
      if (declareType.code.equals(code)) {
        return declareType;
      }
    }
    return null;
  }

  public static DeclareType codeOf(String code) {
    return codeOf(Integer.valueOf(code));
  }
}
