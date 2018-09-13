package com.ljwm.gecko.base.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/9/13 14:35
 */
@Data
public class SpecialPercentVo {

  @ApiModelProperty("项目类型")
  private String name;

  @ApiModelProperty("公司比例")
  private BigDecimal companyPer;

  @ApiModelProperty("个人比例")
  private BigDecimal personPer;
}
