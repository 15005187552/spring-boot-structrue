package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.config.LikeFormatter;
import com.ljwm.gecko.base.entity.ServiceType;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ServiceVo extends ServiceType {

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String avatarPath;

  @JSONField(serializeUsing = LikeFormatter.class)
  private String name;

  private Boolean deleteAble;

  private List<ServiceVo> children;
}
