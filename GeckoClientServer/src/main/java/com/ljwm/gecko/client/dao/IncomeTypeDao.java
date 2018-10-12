package com.ljwm.gecko.client.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.IncomeType;
import com.ljwm.gecko.base.mapper.IncomeTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Janiffy
 * @date 2018/10/11 18:30
 */
@Repository
public class IncomeTypeDao {

  @Autowired
  IncomeTypeMapper incomeTypeMapper;

  public Long selectByName(String name){
    IncomeType incomeType = incomeTypeMapper.selectOne(new QueryWrapper<IncomeType>().eq(IncomeType.NAME, name));
    return incomeType!=null?incomeType.getId():null;
  }
}
