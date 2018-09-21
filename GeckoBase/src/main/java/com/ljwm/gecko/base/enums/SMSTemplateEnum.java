package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/21 14:51
 */
@Getter
public enum SMSTemplateEnum {

  REGISTER(1, "注册模板"),
  MODIFY_PASSWORD(2, "修改密码模板");

  private Integer code;

  private String name;

  SMSTemplateEnum(Integer code, String name){
    this.code = code;
    this.name = name;
  }
}
