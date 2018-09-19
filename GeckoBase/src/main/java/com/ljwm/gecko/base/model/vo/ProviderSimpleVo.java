package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.Provider;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProviderSimpleVo extends Provider {

  @JSONField(serializeUsing = StatusWithNameSerializer.ProviderValidateStatSerializer.class)
  private Integer validateState;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picPath;

  private String locationStr;

  private List<ProviderServicesVo> providerServicesVoList;

  private List<MemberSimpleVo> memberVoList;
}
