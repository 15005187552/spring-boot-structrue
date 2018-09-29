package com.ljwm.gecko.base.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItemQueryDto extends CommonQuery {

  @ApiModelProperty(value = "会员id",hidden = true)
  private Long memberId;

  @ApiModelProperty("服务商id,可以为空")
  private Long providerId;

  @ApiModelProperty("状态 0  取消  5 待服务商定价  8  已定价")
  private Integer status;
}
