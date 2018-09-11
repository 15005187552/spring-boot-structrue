package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.ProviderCustom;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderCustomVo extends ProviderCustom {

  private ProviderVo providerVo;

  private ServeSimpleVo serveSimpleVo;
}
