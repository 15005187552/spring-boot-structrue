package com.ljwm.gecko.im.packet;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.CustomerMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ToProviderPacket extends CustomerMessage {

  private Long sessionId;

  public ToProviderPacket(CustomerMessage customerMessage, Long sessionId) {
    BeanUtil.copyProperties(customerMessage, this);
    this.sessionId = sessionId;
  }
}
