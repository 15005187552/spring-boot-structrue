package com.ljwm.gecko.base.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecItemQueryDto extends CommonQuery {

  @ApiModelProperty("服务规格id")
  private Integer specId;
}
