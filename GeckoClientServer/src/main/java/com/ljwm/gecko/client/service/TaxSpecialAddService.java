package com.ljwm.gecko.client.service;

import com.ljwm.gecko.base.entity.TaxSpecialAdd;
import com.ljwm.gecko.client.dao.TaxSpecialAddDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/11 17:08
 */
@Service
public class TaxSpecialAddService {

  @Autowired
  TaxSpecialAddDao taxSpecialAddDao;

  public BigDecimal getSpecialAddMoney(Long taxId){
    BigDecimal money = BigDecimal.ZERO;
    List<TaxSpecialAdd> list = taxSpecialAddDao.selectListByTaxId(taxId);
    for (TaxSpecialAdd taxSpecialAdd : list) {
      money = money.add(taxSpecialAdd.getTaxMoney());
    }
    return money;
  }
}
