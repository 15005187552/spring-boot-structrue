package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecServicePriceQueryForm {

  @ApiModelProperty("服务商id")
  private Long providerId;

  @ApiModelProperty("服务类型id")
  private Integer serviceId;

  @ApiModelProperty("商品id  可以为空")
  private Long goodId;

  @ApiModelProperty("规格key  按照id从小到大的顺序  如 1_2_3")
  private String key;
}
