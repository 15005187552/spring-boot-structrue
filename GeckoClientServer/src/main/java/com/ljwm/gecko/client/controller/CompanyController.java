package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.CompanyForm;
import com.ljwm.gecko.client.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/9/3 10:50
 */
@RestController
@RequestMapping("company")
@Api(tags = "公司信息 API")
public class CompanyController {

  @Autowired
  CompanyService companyService;

  @PostMapping("/commit")
  @ApiOperation("提交公司信息（包含修改，没有修改执照时，filePath传null）")
  public Result commit(@RequestBody @Valid CompanyForm companyForm){
    return companyService.commit(companyForm);
  }

}
