package com.ljwm.gecko.client.service;

import com.ljwm.gecko.base.entity.TaxOtherReduce;
import com.ljwm.gecko.client.dao.TaxOtherReduceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/11 16:56
 */
@Service
public class TaxOtherReduceService {

  @Autowired
  TaxOtherReduceDao taxOtherReduceDao;

  public BigDecimal getOtherReduceMoney(Long taxId){
    BigDecimal money = BigDecimal.ZERO;
    List<TaxOtherReduce> list = taxOtherReduceDao.selectListByTaxId(taxId);
    for (TaxOtherReduce taxOtherReduce : list) {
      money = money.add(taxOtherReduce.getTaxMoney());
    }
    return money;

  }

}
