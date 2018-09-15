package com.ljwm.gecko.base.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProviderQueryDto extends CommonQuery {

  @ApiModelProperty("状态 审核状态 1待审核 2 审核通过 3 审核失败")
  private Integer validateState;

  @ApiModelProperty("是否可用")
  private Integer disabled;

  @ApiModelProperty("服务类型id")
  private Integer serviceId;
}
