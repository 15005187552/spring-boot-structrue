package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/21 14:51
 */
@Getter
public enum SMSTemplateEnum {

  REGISTER(1, "注册模板", "SMS_147720230"),
  MODIFY_PASSWORD(2, "修改密码模板", "SMS_147720229");

  private Integer code;

  private String name;

  private String templateCode;

  SMSTemplateEnum(Integer code, String name, String templateCode){
    this.code = code;
    this.name = name;
    this.templateCode = templateCode;
  }
}
