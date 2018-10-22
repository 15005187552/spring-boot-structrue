package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.TaxConfirmForm;
import com.ljwm.gecko.client.model.dto.AttendanceForm;
import com.ljwm.gecko.client.model.dto.TaxFindForm;
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
import java.text.ParseException;

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
  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  public Result commit(@RequestBody @Valid AttendanceForm attendanceForm){
    return attendanceService.commit(attendanceForm);
  }

  @PostMapping("/findAttendanceList")
  @ApiOperation("获取工资考勤信息")
  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  public Result findAttendanceList(@RequestBody TaxFindForm taxFindForm){
    return attendanceService.findAttendanceList(taxFindForm);
  }

  @PostMapping("/findAttendanceVoList")
  @ApiOperation("获取工资考勤信息")
  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  public Result findAttendanceVoList(@RequestBody TaxFindForm taxFindForm){
    return attendanceService.findAttendanceVoList(taxFindForm);
  }

  @PostMapping("/findAttendanceAndPersonList")
  @ApiOperation("查看公司申报记录")
  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  public Result findAttendanceAndPersonList(@RequestBody TaxFindForm taxFindForm) throws ParseException {
    return attendanceService.findAttendanceAndPersonList(taxFindForm);
  }

  @PostMapping("/pushToEmployeeConfirm")
  @ApiOperation("推送给员工确认申报记录")
  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  public Result pushToEmployeeConfirm(@RequestBody TaxConfirmForm taxConfirmForm){
    return attendanceService.pushToEmployeeConfirm(taxConfirmForm);
  }

}
