package com.ljwm.gecko.admin.model.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class BaseNumberBean {

  @Excel(name = "下限",numFormat = "0")
  private BigDecimal lower;

  @Excel(name = "上限",orderNum = "1",numFormat = "0")
  private BigDecimal upper;


}
