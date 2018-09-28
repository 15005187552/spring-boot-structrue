package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.CompanyDto;
import com.ljwm.gecko.client.model.dto.TemplateForm;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author Janiffy
 * @date 2018/9/12 12:28
 */
@RestController
@RequestMapping("/template")
@Api(tags = "模板 API")
@Slf4j
public class TemplateController {

  @Autowired
  TemplateService templateService;


 /* @PostMapping("/downloadEmployee")
  @ApiOperation("下载员工信息模板")
  public Result downloadEmployee(HttpServletResponse response) throws IOException {
    return templateService.downloadEmployee(response);
  }*/

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/uploadAttendanceTem")
  @ApiOperation("自定义考勤模板要加的字段")
  public Result uploadTemplate(@RequestBody @Valid TemplateForm templateForm){
    return  templateService.uploadTemplate(templateForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/downloadEmployee")
  @ApiOperation("下载员工信息模板")
  public Result downloadTemplate(HttpServletResponse response) throws IOException {
    return templateService.downloadTemplate(response);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/getEmployeeTem")
  @ApiOperation("获取员工要填写的字段")
  public Result getEmployeeTem(){
    return templateService.getEmployeeTem();
  }

  /*@PostMapping("/uploadAttendanceTem")
  @ApiOperation("自定义考勤模板要加的字段")
  public Result uploadAttendanceTem(@RequestBody @Valid TemplateForm templateForm){
    return  templateService.uploadAttendanceTem(templateForm);
  }*/

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/getAttendanceTem")
  @ApiOperation("获取考勤要填写的字段")
  public Result getAttendanceTem(@RequestBody @Valid CompanyDto companyDto){
    return templateService.getAttendanceTem(companyDto);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/downloadAttendance")
  @ApiOperation("下载考勤信息模板")
  public Result downloadAttendance(HttpServletResponse response, @RequestBody @Valid CompanyDto companyDto) throws IOException {
    return templateService.downloadAttendance(response, companyDto);
  }
}
