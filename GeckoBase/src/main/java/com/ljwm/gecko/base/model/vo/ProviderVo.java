package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.Provider;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProviderVo extends Provider {

  @JSONField(serializeUsing = StatusWithNameSerializer.ProviderValidateStatSerializer.class)
  private Integer validateState;

  private  List<ProviderPaperVo> providerPaperVoList;

}
