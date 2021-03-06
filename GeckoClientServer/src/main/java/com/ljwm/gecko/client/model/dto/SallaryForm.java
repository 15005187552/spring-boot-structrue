package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/10/11 15:18
 */
@Data
public class SallaryForm {

  @ApiModelProperty(value = "申报类型 0-月度申报 1-年度汇缴申报")
  private Integer declareType;

  @ApiModelProperty(value = "申报时段")
  private String declareTime;
}
