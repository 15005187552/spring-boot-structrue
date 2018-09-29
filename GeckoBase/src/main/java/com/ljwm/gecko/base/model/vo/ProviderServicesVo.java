package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.ProviderServices;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ProviderServicesVo extends ProviderServices {

  @JSONField(serializeUsing = StatusWithNameSerializer.ProviderValidateStatSerializer.class)
  private Integer validateState;

  private ServeSimpleVo serveSimpleVo;

  private List<SpecServicesPriceSimpleVo> specServicesPriceSimpleVoList;
}
