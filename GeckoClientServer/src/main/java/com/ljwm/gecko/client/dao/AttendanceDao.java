package com.ljwm.gecko.client.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.Attendance;
import com.ljwm.gecko.base.entity.AttendanceAttribute;
import com.ljwm.gecko.base.mapper.AttendanceAttributeMapper;
import com.ljwm.gecko.base.mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Janiffy
 * @date 2018/9/17 15:10
 */
@Repository
public class AttendanceDao {

  @Autowired
  AttendanceMapper attendanceMapper;

  @Autowired
  AttendanceAttributeMapper attendanceAttributeMapper;

  public String selectByAttribute(String name, Long taxId){
    AttendanceAttribute attendanceAttribute = attendanceAttributeMapper.selectOne(new QueryWrapper<AttendanceAttribute>().like(AttendanceAttribute.NAME, name));
    if (attendanceAttribute != null) {
      Attendance attendance = attendanceMapper.selectOne(new QueryWrapper<Attendance>().eq(Attendance.TAX_ID, taxId).eq(Attendance.ATTRIBUTE_ID, attendanceAttribute.getId()));
      return attendance != null ? attendance.getValue() : null;
    }
    return null;
  }
}
