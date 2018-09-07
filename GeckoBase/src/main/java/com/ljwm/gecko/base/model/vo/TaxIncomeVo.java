package com.ljwm.gecko.base.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/6 14:37
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TaxIncomeVo {

  @ApiModelProperty(value = "类别ID")
  private Long incomeId;

  @ApiModelProperty(value = "分类名称")
  private String name;

  @ApiModelProperty(value = "排序")
  private String sort;

  @ApiModelProperty(value = "是否前台输入 0-不需要 1-需要")
  private Integer isNeedEnter;

  @ApiModelProperty(value = "父分类ID")
  private Long pId;


  @ApiModelProperty(value = "级别 0-一级 1-二级")
  private Integer level;

  @ApiModelProperty(value = "收入金额")
  private String income;

}
