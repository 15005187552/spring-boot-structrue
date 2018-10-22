package com.ljwm.gecko.base.model.vo.admin;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.Protocol;

/**
 * Author: xixil
 * Date: 2018/10/22 10:11
 * RUA
 */

public class ProtocolVO extends Protocol {

  public ProtocolVO(Protocol protocol) {
    if (protocol != null)
      BeanUtil.copyProperties(protocol,this);
  }
}
