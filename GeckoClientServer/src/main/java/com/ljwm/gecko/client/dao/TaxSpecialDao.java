package com.ljwm.gecko.client.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.TaxSpecial;
import com.ljwm.gecko.base.mapper.TaxSpecialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/11 17:03
 */
@Repository
public class TaxSpecialDao {

  @Autowired
  TaxSpecialMapper taxSpecialMapper;

  public List<TaxSpecial> selectListByTaxId(Long taxId){
    return taxSpecialMapper.selectList(new QueryWrapper<TaxSpecial>().eq(TaxSpecial.TAX_ID, taxId));
  }

  public TaxSpecial getTaxSpecial(Long taxId, Long specialDeduId){
    return taxSpecialMapper.selectOne(new QueryWrapper<TaxSpecial>().eq(TaxSpecial.TAX_ID, taxId).eq(TaxSpecial.SPECIAL_DEDU_ID, specialDeduId));
  }
}
