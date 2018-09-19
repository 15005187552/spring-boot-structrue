package com.ljwm.gecko.base.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.SpecialDeduction;
import com.ljwm.gecko.base.mapper.SpecialDeductionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Janiffy
 * @date 2018/9/19 15:17
 */
@Repository
public class SpecialDuctionDao {

  @Autowired
  SpecialDeductionMapper specialDeductionMapper;

  public String idToName(Long id){
    return specialDeductionMapper.selectOne(new QueryWrapper<SpecialDeduction>().eq(SpecialDeduction.ID, id)).getName();
  }
}
