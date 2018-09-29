package com.ljwm.gecko.provider.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecServicesPriceCommonQueryForm {

  @ApiModelProperty("商品id")
  private Long goodId;

  @ApiModelProperty("服务商id")
  private Long providerId;

  @ApiModelProperty("服务类型id")
  private Long serviceId;
}
