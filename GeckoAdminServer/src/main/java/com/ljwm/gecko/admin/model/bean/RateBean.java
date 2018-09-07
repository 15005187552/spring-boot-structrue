package com.ljwm.gecko.admin.model.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class RateBean {

  @Excel(name = "公司" ,orderNum = "4")
  private String company;

  @Excel(name = "个人",orderNum = "5")
  private String personal;
}
