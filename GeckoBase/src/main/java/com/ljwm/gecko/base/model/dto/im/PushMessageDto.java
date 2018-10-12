package com.ljwm.gecko.base.model.dto.im;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.PushMessage;
import com.ljwm.gecko.base.entity.PushMessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Author: xixil
 * Date: 2018/10/11 14:31
 * RUA
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PushMessageDto extends PushMessageType {

  private PushMessage pushMessage;

  public PushMessageDto(PushMessageType pushMessageType,PushMessage pushMessage) {
    BeanUtil.copyProperties(pushMessageType,this);
    this.pushMessage = pushMessage;
  }
}
