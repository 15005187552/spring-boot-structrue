package com.ljwm.gecko.base.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.IncomeType;
import com.ljwm.gecko.base.mapper.IncomeTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Janiffy
 * @date 2018/9/28 21:24
 */
@Repository
public class IncomeTypeDao {
  @Autowired
  IncomeTypeMapper incomeTypeMapper;
  public String idToName(Long id){
    return incomeTypeMapper.selectOne(new QueryWrapper<IncomeType>().eq(IncomeType.ID, id)).getName();
  }

}
