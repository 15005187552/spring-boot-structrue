package com.ljwm.gecko.base.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProviderServicesSimpleVo {

  private Long id;

  private Integer serviceId;

  private String name;
}
