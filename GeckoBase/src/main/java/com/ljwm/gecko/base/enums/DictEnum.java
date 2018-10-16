package com.ljwm.gecko.base.enums;

public enum DictEnum {

  DOWN_PAY_RATE("DownPayRate", "0.3", "最低首付比率");
  private String key;

  private String value;

  private String desc;

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  DictEnum(String key,String value,String desc){
    this.key = key;
    this.value = value;
    this.desc = desc;
  }
}
