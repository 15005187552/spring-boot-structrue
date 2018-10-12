package com.ljwm.gecko.client.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.IncomeType;
import com.ljwm.gecko.base.entity.TaxIncome;
import com.ljwm.gecko.base.mapper.IncomeTypeMapper;
import com.ljwm.gecko.base.mapper.TaxIncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/11 16:28
 */
@Repository
public class TaxIncomeDao {

  @Autowired
  TaxIncomeMapper taxIncomeMapper;

  @Autowired
  IncomeTypeMapper incomeTypeMapper;

  public List<TaxIncome> selectListByTaxId(Long taxId){
    IncomeType incomeType = incomeTypeMapper.selectOne(new QueryWrapper<IncomeType>().like(IncomeType.NAME, "综合所得"));
    if (incomeType != null) {
      return taxIncomeMapper.selectList(new QueryWrapper<TaxIncome>().eq(TaxIncome.TAX_ID, taxId).eq(TaxIncome.INCOME_TYPE_ID, incomeType.getId()));
    }
    return null;
  }

  public TaxIncome selectByIncomeTypeId(Long taxId, Long incomeTypeId){
    return taxIncomeMapper.selectOne(new QueryWrapper<TaxIncome>().eq(TaxIncome.TAX_ID, taxId).eq(TaxIncome.INCOME_TYPE_ID, incomeTypeId));
  }

}
