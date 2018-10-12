package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecDto {

  @ApiModelProperty(value = "主键id")
  private Integer id;

  @ApiModelProperty(value = "服务类型")
  private Integer serviceId;

  @ApiModelProperty(value = "规格名称")
  private String name;

  @ApiModelProperty(value = "排序")
  private Integer sort;

  @ApiModelProperty("父节点")
  private Integer pid;

  @ApiModelProperty("级别")
  private Integer level;
}
