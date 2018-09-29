package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.EmployeeInfoForm;
import com.ljwm.gecko.client.service.CompanyUserService;
import com.ljwm.gecko.client.service.ExcelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;

/**
 * @author Janiffy
 * @date 2018/9/13 14:25
 */
@RestController
@Api(tags = "员工信息 API")
public class EmployeeController {

  @Autowired
  ExcelService excelService;

  @Autowired
  CompanyUserService companyUserService;

  @PostMapping("/commitEmployeeInfo")
  public Result commitEmployeeInfo(@RequestBody  @Valid EmployeeInfoForm employeeInfoForm) throws ParseException {
    return excelService.commitEmployeeInfo(employeeInfoForm);
  }

  @PostMapping("/findEmployeeInfo")
  public Result findEmployeeInfo(@RequestParam("companyId") Long companyId){
    return companyUserService.findEmployeeInfo(companyId);
  }

}
