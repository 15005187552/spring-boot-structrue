package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/8/23 17:40
 */
@Getter
public enum CountryType implements CommonEnum {
  CHINA(0, "中国大陆居民"),
  HTMT(2, "港澳台居民"),
  FOREIGN(3, "外籍居民")
  ;

  private Integer code;
  private String name;

  CountryType(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

}
