package com.ljwm.gecko.base.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.AttendanceAttribute;
import com.ljwm.gecko.base.mapper.AttendanceAttributeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Janiffy
 * @date 2018/9/28 21:22
 */
@Repository
public class AttendanceAttributeDao {
  @Autowired
  AttendanceAttributeMapper attendanceAttributeMapper;

  public String idToName(Long id){
    return attendanceAttributeMapper.selectOne(new QueryWrapper<AttendanceAttribute>().eq(AttendanceAttribute.ID, id)).getName();
  }
}
