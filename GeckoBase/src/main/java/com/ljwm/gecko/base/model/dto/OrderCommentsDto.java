package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderCommentsDto {
  @ApiModelProperty("订单Id")
  private Long id;

  @ApiModelProperty("评论")
  private List<OrderItemCommentsDto> comments;
}
