package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.gecko.admin.enums.CompanyValidateEnum;
import com.ljwm.gecko.admin.model.form.CompanyQuery;
import com.ljwm.gecko.admin.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ljwm.bootbase.dto.Result;

@RestController
@RequestMapping("company")
public class CompanyController extends BaseController {

  @Autowired
  private CompanyService companyService;

  @PostMapping("findUnValidate")
  public Result findUnValidate(@RequestBody CompanyQuery query) {
    query.setValidateStatus(CompanyValidateEnum.UNVALIDATE.getCode());
    return success(companyService.findUnValidate(query));
  }
}
