package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProviderServiceDto {

  @ApiModelProperty("会员资质详情id")
  private Long id;

  @ApiModelProperty("服务id")
  private Integer serviceId;

  @ApiModelProperty("服务商id")
  private Long providerId;
}
