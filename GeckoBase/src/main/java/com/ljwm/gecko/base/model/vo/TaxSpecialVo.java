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
public class TaxSpecialVo {

  @ApiModelProperty(value = "专项扣除分类")
  private Long id;

  @ApiModelProperty(value = "分类名称")
  private String name;

  @ApiModelProperty(value = "排序")
  private String sort;

  @ApiModelProperty(value = "个人缴纳金额")
  private String personalMoney;

  @ApiModelProperty(value = "单位纳税金额")
  private String companyMoney;

  @ApiModelProperty(value = "个人缴纳比例")
  private String personalPercent;

  @ApiModelProperty(value = "单位缴纳比例")
  private String companyPercent;
}
