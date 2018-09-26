package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecItemDto {

  @ApiModelProperty("规格项id")
  private Integer id;

  @ApiModelProperty(value = "规格类型id")
  private Integer specId;

  @ApiModelProperty(value = "规格项")
  private String item;

  @ApiModelProperty(value = "排序")
  private Integer sort;
}
