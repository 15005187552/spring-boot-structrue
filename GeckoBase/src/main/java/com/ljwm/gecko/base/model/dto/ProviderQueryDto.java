package com.ljwm.gecko.base.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProviderQueryDto extends CommonQuery {

  @ApiModelProperty("状态")
  private Integer validateStat;

  @ApiModelProperty("是否可用")
  private Integer disabled;
}
