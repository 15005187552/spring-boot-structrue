package com.ljwm.gecko.base.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/6 14:43
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TaxSpecialAddVo {

  @ApiModelProperty(value = "专项附加扣除分类")
  private Long id;

  @ApiModelProperty(value = "分类名称")
  private String name;

  @ApiModelProperty(value = "个人缴纳金额")
  private String taxMoney;

  @ApiModelProperty(value = "缴纳证明附件路径")
  private String taxDocPath;
}
