package com.ljwm.gecko.base.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.OtherReduce;
import com.ljwm.gecko.base.mapper.OtherReduceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Janiffy
 * @date 2018/9/28 21:28
 */
@Repository
public class OtherReduceDao {

  @Autowired
  OtherReduceMapper otherReduceMapper;

  public String idToName(Long id){
    return otherReduceMapper.selectOne(new QueryWrapper<OtherReduce>().eq(OtherReduce.ID, id)).getName();
  }
}
