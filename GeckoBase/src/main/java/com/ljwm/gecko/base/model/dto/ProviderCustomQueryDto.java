package com.ljwm.gecko.base.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ProviderCustomQueryDto extends CommonQuery {

  @ApiModelProperty("服务商id")
  private Long providerId;

  @ApiModelProperty("服务id")
  private Long serviceId;
}
