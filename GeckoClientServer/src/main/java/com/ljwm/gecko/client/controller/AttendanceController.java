package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.AttendanceForm;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.AttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/9/17 14:58
 */
@RestController
@RequestMapping("/attendance")
@Api(tags = "考勤 API")
public class AttendanceController {

  @Autowired
  AttendanceService attendanceService;

  @PostMapping("/commit")
  @ApiOperation("提交员工考勤信息")
  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  public Result commit(@RequestBody @Valid AttendanceForm attendanceForm){
    return attendanceService.commit(attendanceForm);
  }

  @PostMapping("/getAttendace")
  @ApiOperation("获取当前员工考勤工资信息")
  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  public Result getAttendace(){
    return attendanceService.getAttendace();
  }

}
