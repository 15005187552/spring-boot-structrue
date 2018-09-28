package com.ljwm.gecko.client.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProviderGoodQueryDto extends CommonQuery {

  @ApiModelProperty("服务类型id")
  private Integer serviceId;

  @ApiModelProperty("服务商id")
  private Long providerId;

  @ApiModelProperty(value = "状态",hidden = true)
  private Integer status;
}
