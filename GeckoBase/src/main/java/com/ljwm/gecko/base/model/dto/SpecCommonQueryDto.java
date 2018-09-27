package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecCommonQueryDto {

  @ApiModelProperty("服务类型id")
  private Integer serviceId;
}
