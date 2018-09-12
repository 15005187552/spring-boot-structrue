package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDto {

  @ApiModelProperty(value = "实际付款金额,单位是元,保留两位小数")
  private BigDecimal payment;

  @ApiModelProperty(value = "订单明细")
  private List<String> orderItemNoList;

  @ApiModelProperty("单笔订单立即支付")
  private OrderItemDto orderItemDto;
}
