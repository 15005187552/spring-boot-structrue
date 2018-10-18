package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/21 14:51
 */
@Getter
public enum SMSTemplateEnum implements CommonEnum {

  REGISTER(1, "注册模板", "SMS_147720230", true),
  MODIFY_PASSWORD(2, "修改密码模板", "SMS_147720229", true),
  REMIND_CONFIRM(3, "提醒员工确认工资条通知", "SMS_148610255", false);


  private Integer code;

  private String name;

  private String templateCode;

  private boolean isSMSCode;

  SMSTemplateEnum(Integer code, String name, String templateCode, boolean isSMSCode){
    this.code = code;
    this.name = name;
    this.templateCode = templateCode;
    this.isSMSCode = isSMSCode;
  }
}
