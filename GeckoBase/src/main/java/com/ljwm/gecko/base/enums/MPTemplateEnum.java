package com.ljwm.gecko.base.enums;


import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * Created by user on 2017/7/1.
 */
@Getter
public enum MPTemplateEnum implements CommonEnum {

  ORDER_STATUS(0, "客户订单状态通知", "moEC2yetUJhOzifzA7PQ-2qLRMPBeuRik2d8erDOO0w"),
  AUDIT(1, "审核结果通知", "QZsREF95AL6gm5pG6PTERbR6COtjFqqTQvEEllmsi_M"),
  PROVIDER_ORDER_STATUS(2, "服务商订单状态通知", "moEC2yetUJhOzifzA7PQ-5jcCVPSA4KuwoTuszj3hH4"),
  REMIND_CONFIRM(3, "员工薪资确认通知", "SjgbUZouUMyY1zk8zS3fF2MX09Pk0CDNdI6DeYaF5bA")
  ;

  private Integer code;

  private String name;

  private String templateId;

  MPTemplateEnum(Integer code, String name, String templateId) {
    this.code = code;
    this.name = name;
    this.templateId = templateId;
  }

}
