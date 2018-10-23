package com.ljwm.gecko.base.model.vo.admin;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.Protocol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Author: xixil
 * Date: 2018/10/22 10:11
 * RUA
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ProtocolVO extends Protocol {

  public ProtocolVO(Protocol protocol) {
    if (protocol != null)
      BeanUtil.copyProperties(protocol,this);
  }
}
