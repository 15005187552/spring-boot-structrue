package com.ljwm.gecko.client.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/10/15 15:12
 */
@Data
public class TaxConfirmForm {

  @ApiModelProperty(value = "公司ID")
  private Long companyId;

  @ApiModelProperty(value = "申报类型 0-月报 1-年报")
  private Integer declareType;

  @ApiModelProperty(value = "申报时段")
  private String declareTime;

  @ApiModelProperty("id数组")
  private Long[] ids;
}
