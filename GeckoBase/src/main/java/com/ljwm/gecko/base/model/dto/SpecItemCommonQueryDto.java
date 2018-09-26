package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecItemCommonQueryDto {

  @ApiModelProperty("服务规格id")
  private Integer specId;
}
