package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecialDeductionDto {

  @ApiModelProperty(value = "专项扣除分类")
  private Long id;

  @ApiModelProperty(value = "分类名称")
  private String name;

  @ApiModelProperty(value = "排序")
  private Integer sort;
}
