package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IncomeTypeDto {

  @ApiModelProperty("主键id")
  private Long id;

  @ApiModelProperty(value = "分类名称")
  private String name;

  @ApiModelProperty(value = "排序")
  private String sort;

  @ApiModelProperty(value = "是否前台输入 0-不需要 1-需要")
  private Integer isNeedEnter;

  @ApiModelProperty(value = "父分类ID")
  private Long pId;

  @ApiModelProperty(value = "分类描述")
  private String classDesc;

  @ApiModelProperty(value = "级别 0-一级 1-二级")
  private Integer level;
}
