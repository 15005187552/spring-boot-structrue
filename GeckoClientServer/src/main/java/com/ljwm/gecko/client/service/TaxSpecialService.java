package com.ljwm.gecko.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.SpecialDeduction;
import com.ljwm.gecko.base.entity.TaxSpecial;
import com.ljwm.gecko.base.mapper.SpecialDeductionMapper;
import com.ljwm.gecko.client.dao.TaxSpecialDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/11 17:02
 */
@Service
public class TaxSpecialService {

  @Autowired
  TaxSpecialDao taxSpecialDao;

  @Autowired
  SpecialDeductionMapper specialDeductionMapper;

  public BigDecimal getSpecialMoney(Long taxId){
    BigDecimal money = BigDecimal.ZERO;
    List<TaxSpecial> list = taxSpecialDao.selectListByTaxId(taxId);
    for (TaxSpecial taxSpecial : list) {
      money = money.add(taxSpecial.getPersonalMoney());
    }
    return money;
  }

  public BigDecimal getFundMoney(Long taxId){
    SpecialDeduction specialDeduction = specialDeductionMapper.selectOne(new QueryWrapper<SpecialDeduction>().like(SpecialDeduction.NAME, "公积金"));
    if (specialDeduction != null) {
      return taxSpecialDao.getTaxSpecial(taxId, specialDeduction.getId()).getPersonalMoney();
    }
    return BigDecimal.ZERO;
  }

  public BigDecimal getSocialMoney(Long taxId){
    return getSpecialMoney(taxId).subtract(getFundMoney(taxId));
  }
}
