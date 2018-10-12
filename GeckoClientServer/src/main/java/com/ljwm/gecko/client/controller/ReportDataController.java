package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.ReportDataVo;
import com.ljwm.gecko.base.service.ReportDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reportData")
@Api(tags = "个人数据申报---客户端")
public class ReportDataController extends BaseController {

  @Autowired
  private ReportDataService reportDataService;

  @GetMapping("/findReportData")
  @ApiOperation(value = "查询个人申报基础结构")
  public Result<ReportDataVo> findReportData(){
    return success(reportDataService.findReportData());
  }

}
