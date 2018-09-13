package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/9/13 15:28
 */
@Data
public class SpecailForm {

  @ApiModelProperty("公司ID")
  private Long companyId;

  @ApiModelProperty(value = "公司养老保险比例")
  private BigDecimal comPension;

  @ApiModelProperty(value = "公司医疗保险比例")
  private BigDecimal comMedical;

  @ApiModelProperty(value = "公司失业保险比例")
  private BigDecimal comUnemploy;

  @ApiModelProperty(value = "公司工伤保险比例")
  private BigDecimal comInjury;

  @ApiModelProperty(value = "公司生育保险比例")
  private BigDecimal comBirth;

  @ApiModelProperty(value = "个人养老保险比例")
  private BigDecimal personPension;

  @ApiModelProperty(value = "个人医疗保险比例")
  private BigDecimal personMedical;

  @ApiModelProperty(value = "个人失业保险比例")
  private BigDecimal personUnemploy;

  @ApiModelProperty(value = "个人工伤比例")
  private BigDecimal peesonInjury;

  @ApiModelProperty(value = "个人生育比例")
  private BigDecimal personBirth;

  @ApiModelProperty(value = "公司公积金比例")
  private BigDecimal fundCom;

  @ApiModelProperty(value = "个人公积金比例")
  private BigDecimal fundPerson;
}
