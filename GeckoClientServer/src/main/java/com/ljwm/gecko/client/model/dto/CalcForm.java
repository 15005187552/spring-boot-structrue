package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/9/7 9:56
 */
@Data
@Accessors(chain = true)
@ApiModel("个税计算器提交数据表单")
public class CalcForm {

  private Integer code;

  private BigDecimal money;
}
