package com.ljwm.gecko.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocketChannelEnum {

  CLIENT(0,"客户端渠道",4),
  MEMBER(1,"会员渠道",4),
  ADMIN(2,"后端渠道",5),
  PROVIDER(3,"服务端渠道",6);

  private Integer code;

  private String info;

  private Integer tableCode;
}
