package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/6 14:16
 */
@Data
@Accessors(chain = true)
@ApiModel("查看个税表单")
public class TaxInfoForm {

  @ApiModelProperty(value = "会员ID")
  private Long memberId;

  @ApiModelProperty(value = "申报类型 0-月度申报 1-年度汇缴申报")
  private Integer declareType;

  @ApiModelProperty(value = "申报时段")
  private String declareTime;
}
