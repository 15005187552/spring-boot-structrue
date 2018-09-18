package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderItemCommentsDto {

  @ApiModelProperty("订单明细ID")
  private Long id;

  @ApiModelProperty("图片，逗号分隔")
  private List<String> pictures;

  @ApiModelProperty("评论内容")
  private String text;

  @ApiModelProperty("星级")
  private Integer star;
}
