package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.AttendanceQuery;
import com.ljwm.gecko.admin.model.form.AttendanceSaveForm;
import com.ljwm.gecko.admin.service.MaintenanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("attendance")
@Api(tags = "考勤管理 API")
public class AttendanceController extends BaseController {

  @Autowired
  private MaintenanceService maintenanceService;

  @PostMapping("save")
  @ApiOperation("考勤字段 保存")
  public Result save(@RequestBody AttendanceSaveForm form) {
    return success(maintenanceService.save(form));
  }

  @PostMapping("find")
  @ApiOperation("查看")
  public Result find(@RequestBody AttendanceQuery query) {
    return success(maintenanceService.find(query));
  }


  @GetMapping("delete/{id}")
  public void delete(@PathVariable Long id) {
    maintenanceService.delete(id);
  }
}
