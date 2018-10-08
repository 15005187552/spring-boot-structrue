package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ServeDto {

  @ApiModelProperty(value = "ID")
  private Integer id;

  @ApiModelProperty(value = "服务分类名称")
  private String name;

  @ApiModelProperty(value = "父级服务分类ID")
  private Integer pid;

  @ApiModelProperty(value = "分类级别 一级 0 二级 1 ")
  private Integer level;

  @ApiModelProperty(value = "最小额度 ")
  private BigDecimal minMoney;
}
