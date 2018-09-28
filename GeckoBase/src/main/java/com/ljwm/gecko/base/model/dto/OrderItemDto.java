package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderItemDto {

  @ApiModelProperty(value = "服务id")
  private Integer serviceId;

  @ApiModelProperty(value = "商品服务明细id")
  private Long goodId;

  @ApiModelProperty(value = "商品名称")
  private String serviceName;

  @ApiModelProperty(value = "生成订单时的商品单价，单位是元,保留两位小数")
  private BigDecimal currentUnitPrice;

  @ApiModelProperty(value = "商品数量",hidden = true)
  private Integer quantity=1;

  @ApiModelProperty(value = "服务订单备注")
  private String serviceContent;

  @ApiModelProperty("服务商id")
  private Long providerId;

  @ApiModelProperty("商品规格id")
  private Long specServiceId;

  @ApiModelProperty(value = "会员id",hidden = true)
  private Long memberId;

}
