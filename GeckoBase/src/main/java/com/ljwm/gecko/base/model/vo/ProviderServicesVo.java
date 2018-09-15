package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.ProviderServices;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProviderServicesVo extends ProviderServices {

  private ServeSimpleVo serveSimpleVo;
}
