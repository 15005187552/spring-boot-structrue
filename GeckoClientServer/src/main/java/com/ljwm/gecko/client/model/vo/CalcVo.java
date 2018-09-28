package com.ljwm.gecko.client.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/9/7 11:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcVo {

  @ApiModelProperty("个税降幅")
  private BigDecimal percent;

  @ApiModelProperty("差异")
  private BigDecimal difference;

  @ApiModelProperty("旧个税")
  private BigDecimal oldTax;

  @ApiModelProperty("新个税")
  private BigDecimal newTax;

  @ApiModelProperty("社保")
  private BigDecimal socialSurance;
}
