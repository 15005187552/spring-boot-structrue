package com.ljwm.gecko.client.service;

import com.ljwm.gecko.base.entity.TaxIncome;
import com.ljwm.gecko.client.dao.IncomeTypeDao;
import com.ljwm.gecko.client.dao.TaxIncomeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/11 16:24
 */
@Service
public class TaxIncomeService {

  @Autowired
  TaxIncomeDao taxIncomeDao;

  @Autowired
  IncomeTypeDao incomeTypeDao;


  public BigDecimal getIncomeMoney(Long taxId){
    BigDecimal income = BigDecimal.ZERO;
    List<TaxIncome> list = taxIncomeDao.selectListByTaxId(taxId);
    for (TaxIncome taxIncome : list) {
      income = income.add(taxIncome.getIncome());
    }
    return income;
  }

  public BigDecimal getMoney(Long taxId, String name){
    Long incomeTypeId = incomeTypeDao.selectByName(name);
    TaxIncome taxIncome = taxIncomeDao.selectByIncomeTypeId(taxId, incomeTypeId);
    return taxIncome!=null?taxIncome.getIncome():null;
  }

}
