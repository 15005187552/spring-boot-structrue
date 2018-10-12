package com.ljwm.gecko.client.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/10/11 17:34
 */
@Data
@Accessors(chain = true)
public class SallaryVo {

  @ApiModelProperty("工号")
  private String jobNum;

  @ApiModelProperty("姓名")
  private String name;

  @ApiModelProperty("基本工资")
  private String baseSallary;

  @ApiModelProperty("奖金津贴补贴")
  private String bonus;

  @ApiModelProperty("病事假扣款")
  private String sickDeduc;

  @ApiModelProperty("其他扣款")
  private String otherDeduc;

  @ApiModelProperty("个人社保缴费")
  private String personSocial;

  @ApiModelProperty("个人住房公积金缴费")
  private String personFund;

  @ApiModelProperty("全年一次性奖金")
  private String yearBonus;

  @ApiModelProperty("解除劳动关系补偿金")
  private String compensation;

  @ApiModelProperty("个人所得税")
  private String personTax;

  @ApiModelProperty("税后工资")
  private String afterTax;

}
