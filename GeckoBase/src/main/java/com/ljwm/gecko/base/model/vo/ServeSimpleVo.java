package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.Service;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServeSimpleVo extends Service {

  public ServeSimpleVo(Service service){
    if (service!=null){
      BeanUtil.copyProperties(service,this);
    }
  }
}
