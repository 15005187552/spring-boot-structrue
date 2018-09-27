package com.ljwm.gecko.provider.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class SpecServicesPriceForm {

  @ApiModelProperty(value = "规格键名")
  private Integer id;

  @ApiModelProperty(value = "规格键名")
  private String key;

  @ApiModelProperty(value = "规格键名中文")
  private String keyName;

  @ApiModelProperty(value = "价格")
  private BigDecimal price;

  @ApiModelProperty(value = "首付比率")
  private BigDecimal downPaymentRate;

  @ApiModelProperty(value = "服务商id",hidden = true)
  private Long providerId;

  @ApiModelProperty(value = "是否启用   0  启用  1 不启用")
  private Integer disabled;

  @ApiModelProperty(value = "商品id")
  private Long goodId;

}
