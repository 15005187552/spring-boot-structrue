package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.AttendanceModel;
import com.ljwm.gecko.client.model.dto.NormalSalaryForm;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

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

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/personInfo/import")
  @ApiOperation("人员信息导入")
  public Result importPersonInfo(@RequestParam("file")MultipartFile file, @RequestParam("companyId")Long companyId) throws Exception {
    excelService.importPersonInfo(file, companyId);
    return Result.success("导入成功！");
  }

 /* @PostMapping("/attendance/import")
  public Result importAttendance(@RequestParam("file")MultipartFile file, @RequestParam("companyId")Long companyId,
                                 @RequestParam("date")String date) throws Exception {
    excelService.improtAttendance(file, companyId, date);
    return Result.success("导入成功！");
  }*/

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/personInfo/export")
  @ApiOperation("人员信息导出")
  public Result exportPersonInfoExcel(HttpServletResponse response, @RequestParam("companyId")Long companyId) throws IOException, ParseException {
    return Result.success(excelService.exportPersonInfoExcel(response, companyId, null));
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/normalSalary/export")
  @ApiOperation("正常工资薪金导出")
  public Result exportNormalSalary(HttpServletResponse response, @RequestBody NormalSalaryForm normalSalaryForm)throws IOException {
    return Result.success(excelService.exportNormalSalary(response, normalSalaryForm, null));
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/attendance/import")
  @ApiOperation("导入员工考勤信息")
  public Result importAttendance(@RequestParam("file")MultipartFile file, @RequestParam("companyId")Long companyId, @RequestParam("declareTime") String declareTime, @RequestParam("declareType") Integer declareType)throws IOException {
    return excelService.importAttendance(file, companyId, declareTime, declareType);
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/attendance/export")
  @ApiOperation("工资考勤信息导出")
  public Result exportPersonInfoExcel(HttpServletResponse response, @RequestBody AttendanceModel attendanceDto)throws IOException {
    return Result.success(excelService.exportAttendanceExcel(response, attendanceDto));
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("exportZip")
  @ApiOperation("一键导出压缩包")
  public Result exportZip(HttpServletResponse response, @RequestBody NormalSalaryForm normalSalaryForm) throws IOException, ParseException {
    return Result.success(excelService.exportZip(response, normalSalaryForm));
  }

}
