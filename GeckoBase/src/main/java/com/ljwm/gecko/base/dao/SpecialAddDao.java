package com.ljwm.gecko.base.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.AddSpecial;
import com.ljwm.gecko.base.mapper.AddSpecialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Janiffy
 * @date 2018/9/28 21:19
 */
@Repository
public class SpecialAddDao {

  @Autowired
  AddSpecialMapper addSpecialMapper;

  public String idToName(Long id){
    return addSpecialMapper.selectOne(new QueryWrapper<AddSpecial>().eq(AddSpecial.ID, id)).getName();
  }
}
