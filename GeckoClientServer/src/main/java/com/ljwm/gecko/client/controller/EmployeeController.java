package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.EmployeeInfoForm;
import com.ljwm.gecko.client.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/9/13 14:25
 */
@RestController
public class EmployeeController {

  @Autowired
  ExcelService excelService;

  @PostMapping("/commitEmployeeInfo")
  public Result commitEmployeeInfo(@RequestBody  @Valid EmployeeInfoForm employeeInfoForm) {
    return excelService.commitEmployeeInfo(employeeInfoForm);
  }

}
