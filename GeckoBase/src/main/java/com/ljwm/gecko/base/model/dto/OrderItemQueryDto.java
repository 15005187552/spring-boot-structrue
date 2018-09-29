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

  @ApiModelProperty("状态")
  private Integer status;

  @ApiModelProperty("服务商id")
  private Long providerId;
}
