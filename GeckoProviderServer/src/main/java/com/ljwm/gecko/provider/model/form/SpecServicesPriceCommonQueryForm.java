package com.ljwm.gecko.provider.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecServicesPriceCommonQueryForm {

  @ApiModelProperty("商品id")
  private Long goodId;

  @ApiModelProperty(value = "服务商id",hidden = true)
  private Long providerId;
}
