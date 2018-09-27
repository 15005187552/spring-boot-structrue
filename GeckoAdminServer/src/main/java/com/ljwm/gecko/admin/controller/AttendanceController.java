package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.AttendanceQuery;
import com.ljwm.gecko.admin.model.form.AttendanceSaveForm;
import com.ljwm.gecko.admin.service.MaintenanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("attendance")
@Api(tags = "考勤管理 API")
public class AttendanceController extends BaseController {

  @Autowired
  private MaintenanceService maintenanceService;

  @PostMapping("save")
  @ApiOperation("考勤字段 保存")
  public Result save(AttendanceSaveForm form) {
    return success(maintenanceService.save(form));
  }

  @PostMapping("find")
  @ApiOperation("查看")
  public Result find(AttendanceQuery query) {
    return success(maintenanceService.find(query));
  }


  @GetMapping("delete/{id}")
  public void delete(Long id) {
  }


}
