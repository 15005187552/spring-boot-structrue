package com.ljwm.gecko.base.enums;


/**
 * Created by user on 2017/7/1.
 */
public enum MPTemplateEnum {

  ORDER_STATUS("客户订单状态通知","moEC2yetUJhOzifzA7PQ-2qLRMPBeuRik2d8erDOO0w"),
  AUDIT("审核结果通知","QZsREF95AL6gm5pG6PTERbR6COtjFqqTQvEEllmsi_M"),
  PROVIDER_ORDER_STATUS("服务商订单状态通知","moEC2yetUJhOzifzA7PQ-5jcCVPSA4KuwoTuszj3hH4");
  private String name;
  private String templateId;

  public String getName() {
    return name;
  }

  public String getTemplateId() {
    return templateId;
  }


  MPTemplateEnum(String name, String templateId ) {
    this.name = name;
    this.templateId = templateId;
  }

}
