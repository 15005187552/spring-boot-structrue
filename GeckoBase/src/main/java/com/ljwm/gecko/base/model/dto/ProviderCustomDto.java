package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProviderCustomDto {

  @ApiModelProperty(value = "服务明细id")
  private Long id;

  @ApiModelProperty(value = "服务id,对应t_service表的主键")
  private Long serviceId;

  @ApiModelProperty(value = "服务商id")
  private Long providerId;

  @ApiModelProperty(value = "服务明细名称")
  private String name;

  @ApiModelProperty(value = "商品副标题  暂时不传")
  private String subtitle;

  @ApiModelProperty(value = "产品主图,url相对地址   暂时不传")
  private String mainImage;

  @ApiModelProperty(value = "商品详情")
  private String detail;

  @ApiModelProperty(value = "价格,单位-元保留两位小数")
  private BigDecimal price;
}
