package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/5 17:24
 */
@Data
@Accessors(chain = true)
@ApiModel("专项附加扣除表单")
public class TaxSpecialAddForm {

  @ApiModelProperty(value = "专项附加扣除分类ID")
  private Long specialAddId;

  @ApiModelProperty(value = "个人缴纳金额")
  private String taxMoney;

  @ApiModelProperty(value = "缴纳证明附件路径")
  private String taxDocPath;

}
