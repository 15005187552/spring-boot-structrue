package com.ljwm.gecko.admin.model.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.ljwm.gecko.base.entity.CityItem;
import com.ljwm.gecko.base.model.vo.RateVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class LocationExcelBean {

  @Excel(name = "项目", orderNum = "0")
  private String project;

  @ExcelEntity(name = "基数", show = true)
  private BaseNumberBean baseNumber;

  @ExcelEntity(name = "比例", show = true, id = "rate")
  private RateBean rate;


  public LocationExcelBean(RateVo rateVo) {
    this.project = rateVo.getTypeName();
    this.rate = new RateBean(
      Objects.equals(rateVo.getCompanyPer().intValue(), rateVo.getCompanyPer()) ?
        rateVo.getCompanyPer().setScale(2, BigDecimal.ROUND_HALF_UP).toString()
        : rateVo.getCompanyPer().multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%"
      , Objects.equals(rateVo.getPersonPer().intValue(), rateVo.getPersonPer()) ?
      rateVo.getPersonPer().setScale(2, BigDecimal.ROUND_HALF_UP).toString()
      : rateVo.getPersonPer().multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%"
    );
    this.baseNumber = new BaseNumberBean(rateVo.getLowerLimit(), rateVo.getUpperLimit());
  }
}
