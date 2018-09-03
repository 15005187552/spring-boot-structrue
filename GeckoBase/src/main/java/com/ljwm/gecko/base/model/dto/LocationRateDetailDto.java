package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class LocationRateDetailDto {

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "上限")
  private BigDecimal upperLimit;

  @ApiModelProperty(value = "下限")
  private BigDecimal lowerLimit;

  @ApiModelProperty(value = "单位比例")
  private BigDecimal companyPer;

  @ApiModelProperty(value = "个人比例")
  private BigDecimal personPer;

  @ApiModelProperty(value = "比例类型 0-百分比 1-金额")
  private Integer perType;
}
