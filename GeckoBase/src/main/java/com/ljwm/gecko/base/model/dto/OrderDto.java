package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDto {
  @ApiModelProperty(value = "订单明细")
  private List<Long> orderItemNoList;
}
