package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/5 16:35
 */
@Data
@Accessors(chain = true)
@ApiModel("收入额表单")
public class TaxIncomeForm {

  @ApiModelProperty(value = "收入分类ID")
  private Long incomeTypeId;

  @ApiModelProperty(value = "收入金额")
  private String income;

  @ApiModelProperty("pId")
  private Long pId;
}
