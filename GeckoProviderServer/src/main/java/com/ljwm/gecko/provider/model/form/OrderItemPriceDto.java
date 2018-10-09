package com.ljwm.gecko.provider.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderItemPriceDto {

  @ApiModelProperty("id")
  private Long id;

  @ApiModelProperty("价格")
  private BigDecimal price;
}
