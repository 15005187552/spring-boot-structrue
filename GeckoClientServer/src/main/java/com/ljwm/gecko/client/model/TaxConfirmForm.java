package com.ljwm.gecko.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Janiffy
 * @date 2018/10/15 15:12
 */
@Data
public class TaxConfirmForm {

  @ApiModelProperty(value = "公司ID")
  private Long companyId;

  @ApiModelProperty(value = "申报类型 0-月度申报 1-年度汇缴申报")
  private Integer declareType;

  @NotNull
  @ApiModelProperty(value = "申报时段")
  private String declareTime;

  @ApiModelProperty("id数组")
  private Long[] ids;
}
