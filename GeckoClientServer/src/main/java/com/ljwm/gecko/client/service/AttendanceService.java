package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.Attendance;
import com.ljwm.gecko.client.dao.AttendanceDao;
import com.ljwm.gecko.client.model.dto.AttendanceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Janiffy
 * @date 2018/9/17 15:06
 */
@Service
public class AttendanceService {

  @Autowired
  AttendanceDao attendanceDao;

  public Result commit(AttendanceForm attendanceForm) {
    Attendance attendance = new Attendance();
    BeanUtil.copyProperties(attendanceForm, attendance);
    attendanceDao.insertOrUpdate(attendance);
    return Result.success(attendance);
  }
}
