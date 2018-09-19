package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Janiffy
 * @date 2018/9/10 15:05
 */
@Slf4j
@RequestMapping("/excel")
@RestController
@Api(tags = "excel相关 API")
public class ExcelController {

  @Autowired
  ExcelService excelService;

  @PostMapping("/personInfo/import")
  @ApiOperation("人员信息导入")
  public Result improtPersonInfo(@RequestParam("file")MultipartFile file, @RequestParam("companyId")Long companyId) throws Exception {
    excelService.improtPersonInfo(file, companyId);
    return Result.success("导入成功！");
  }

 /* @PostMapping("/attendance/import")
  public Result importAttendance(@RequestParam("file")MultipartFile file, @RequestParam("companyId")Long companyId,
                                 @RequestParam("date")String date) throws Exception {
    excelService.improtAttendance(file, companyId, date);
    return Result.success("导入成功！");
  }*/

  @PostMapping("/personInfo/export")
  @ApiOperation("人员信息导出")
  public Result exportPersonInfoExcel(HttpServletResponse response, @RequestParam("companyId")Long companyId)throws IOException {
    return Result.success(excelService.exportPersonInfoExcel(response, companyId));
  }

  @PostMapping("/normalSalary/export")
  @ApiOperation("正常工资薪金")
  public Result exportNormalSalary(HttpServletResponse response, @RequestParam("companyId")Long companyId)throws IOException {
    return Result.success(excelService.exportNormalSalary(response, companyId));
  }
}
