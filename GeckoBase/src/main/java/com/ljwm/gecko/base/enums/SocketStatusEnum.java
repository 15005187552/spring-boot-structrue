package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * SocketStatusEnum  Created by yunqisong on 2018/9/24.
 * 连接状态枚举
 */
@Getter
public enum SocketStatusEnum {

  ON_LINE(1, "在线"),
  OFF_LINE(-1, "离线");

  private Integer code;

  private String info;

  SocketStatusEnum(Integer code, String info) {
    this.code = code;
    this.info = info;
  }
}

