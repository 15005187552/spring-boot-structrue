package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.ServiceType;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ServeSimpleVo extends ServiceType {

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String avatarPath;

  private String pName;

  public ServeSimpleVo(ServiceType service){
    if (service!=null){
      BeanUtil.copyProperties(service,this);
    }
  }
}
