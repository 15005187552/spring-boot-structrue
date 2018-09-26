package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.CompanyDto;
import com.ljwm.gecko.client.model.dto.TemplateForm;
import com.ljwm.gecko.client.service.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


  @PostMapping("/uploadEmployeeTemplate")
  @ApiOperation("自定义模板要加的字段")
  public Result uploadTemplate(@RequestBody @Valid TemplateForm templateForm){
    return  templateService.uploadTemplate(templateForm);
  }

  @PostMapping("/downloadEmployee")
  @ApiOperation("下载模板")
  public Result downloadTemplate(HttpServletResponse response, @RequestBody @Valid CompanyDto companyDto) throws IOException {
    return templateService.downloadTemplate(response, companyDto);
  }

  @PostMapping("/getEmployeeTem")
  @ApiOperation("获取员工要填写的字段")
  public Result getEmployeeTem(@RequestBody @Valid CompanyDto companyDto){
    return templateService.getEmployeeTem(companyDto);
  }
}
