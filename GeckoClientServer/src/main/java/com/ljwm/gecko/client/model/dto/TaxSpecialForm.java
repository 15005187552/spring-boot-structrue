package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/5 17:20
 */
@Data
@Accessors(chain = true)
@ApiModel("专项扣除表单")
public class TaxSpecialForm {

  @ApiModelProperty(value = "专项扣除分类ID")
  private Long specialDeduId;

  @ApiModelProperty(value = "个人缴纳金额")
  private String personalMoney;

  @ApiModelProperty(value = "单位纳税金额")
  private String companyMoney;

  @ApiModelProperty(value = "个人缴纳比例")
  private String personalPercent;

  @ApiModelProperty(value = "单位缴纳比例")
  private String companyPercent;

}
