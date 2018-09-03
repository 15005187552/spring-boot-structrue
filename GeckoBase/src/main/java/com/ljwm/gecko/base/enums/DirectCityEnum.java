package com.ljwm.gecko.base.enums;

public enum DirectCityEnum {
  BEIJING(110000, "北京"),
  TIANJIN(120000, "天津"),
  SHANGHAI(310000, "上海"),
  CHONGQING(500000, "重庆");

  private Integer code;
  private String name;

  DirectCityEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public Integer getCode() {
    return code;
  }

  public String getName() {
    return name;
  }


  public static String getName(Integer code) {
    for (DirectCityEnum directCityEnum : values()) {
      if (directCityEnum.getCode().intValue() == code.intValue()) {
        return directCityEnum.getName();
      }
    }
    return "";
  }
}
