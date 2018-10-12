package com.ljwm.gecko.client.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.TaxOtherReduce;
import com.ljwm.gecko.base.mapper.TaxOtherReduceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/11 16:58
 */
@Repository
public class TaxOtherReduceDao {

  @Autowired
  TaxOtherReduceMapper taxOtherReduceMapper;

  public List<TaxOtherReduce> selectListByTaxId(Long taxId){
    return taxOtherReduceMapper.selectList(new QueryWrapper<TaxOtherReduce>().eq(TaxOtherReduce.TAX_ID, taxId));
  }

}
