package com.ljwm.gecko.base.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderQueryDto extends CommonQuery {

  @ApiModelProperty("订单状态")
  private Integer status;
}
