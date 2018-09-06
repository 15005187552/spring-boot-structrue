package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/5 17:07
 */
@Data
@Accessors(chain = true)
@ApiModel("其它扣除减免表单")
public class TaxOtherReduceForm {

  @ApiModelProperty(value = "其它扣除减免分类ID")
  private Long otherReduceId;

  @ApiModelProperty(value = "个人缴纳金额")
  private String taxMoney;

  @ApiModelProperty(value = "缴纳证明路径")
  private String taxDocPath;

}
