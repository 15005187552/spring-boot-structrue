package com.ljwm.gecko.client.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/10/8 11:20
 */
@Data
@Accessors(chain = true)
public class TaxResultVo {

  @ApiModelProperty("社保费用")
  private BigDecimal socialFee;

  @ApiModelProperty("纳税金额")
  private BigDecimal tax;

  @ApiModelProperty("税后金额")
  private BigDecimal afterTax;

  @ApiModelProperty("收入构成")
  private String incomeAdvice;

  @ApiModelProperty("扣除")
  private String deducAdvice;

}
