package com.ljwm.gecko.client.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/10/18 14:19
 */
@Data
@Accessors(chain = true)
public class AdviceVo {

  @ApiModelProperty("纳税金额")
  private BigDecimal tax;

  @ApiModelProperty("税后金额")
  private BigDecimal afterTax;

  @ApiModelProperty("收入构成建议")
  private String incomeAdvice;

  @ApiModelProperty("扣除建议")
  private String deducAdvice;
}
