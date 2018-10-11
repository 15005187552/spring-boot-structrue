package com.ljwm.gecko.im.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.PushMessage;
import com.ljwm.gecko.base.entity.PushMessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Author: xixil
 * Date: 2018/10/10 11:22
 * RUA
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PushMessageVo extends PushMessage {

  private List<PushMessageType> types;

  public PushMessageVo(PushMessage pushMessage) {
    BeanUtil.copyProperties(pushMessage,this);
  }
}
