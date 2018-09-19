package com.ljwm.gecko.client.dao;

import com.ljwm.gecko.base.entity.CalTax;
import com.ljwm.gecko.base.mapper.CalTaxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/9/7 14:06
 */
@Repository
public class CalTaxDao {

  @Autowired
  CalTaxMapper calTaxMapper;

  public void insertOrUpdate(CalTax calTax, BigDecimal diff) {
    CalTax cal = calTaxMapper.selectById(calTax.getGuestId());
    if(cal != null){
      if(diff.compareTo(BigDecimal.ZERO)>=0){
        calTax.setCreateTime(cal.getCreateTime());
        calTaxMapper.updateById(calTax);
      }
    } else {
      calTaxMapper.insert(calTax);
    }
  }
}
