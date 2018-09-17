package com.ljwm.gecko.client.dao;

import com.ljwm.gecko.base.entity.Attendance;
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

  public void insertOrUpdate(Attendance attendance) {
    if(attendance.getId() != null){
      attendanceMapper.updateById(attendance);
    } else {
      attendanceMapper.insert(attendance);
    }
  }
}
