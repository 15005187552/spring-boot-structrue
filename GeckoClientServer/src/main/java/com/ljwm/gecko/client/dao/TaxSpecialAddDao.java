package com.ljwm.gecko.client.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.TaxSpecialAdd;
import com.ljwm.gecko.base.mapper.TaxSpecialAddMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/11 17:05
 */
@Repository
public class TaxSpecialAddDao {

  @Autowired
  TaxSpecialAddMapper taxSpecialAddMapper;

  public List<TaxSpecialAdd> selectListByTaxId(Long taxId){
    return taxSpecialAddMapper.selectList(new QueryWrapper<TaxSpecialAdd>().eq(TaxSpecialAdd.TAX_ID, taxId));
  }
}
