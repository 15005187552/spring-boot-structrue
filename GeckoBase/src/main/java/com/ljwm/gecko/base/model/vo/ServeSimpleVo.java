package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.ServiceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServeSimpleVo extends ServiceType {

  public ServeSimpleVo(ServiceType service){
    if (service!=null){
      BeanUtil.copyProperties(service,this);
    }
  }
}
