package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.gecko.admin.enums.CompanyValidateEnum;
import com.ljwm.gecko.admin.model.form.CompanyQuery;
import com.ljwm.gecko.admin.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ljwm.bootbase.dto.Result;

@RestController
@RequestMapping("company")
@Api(tags = "后管公司 API")
public class CompanyController extends BaseController {

  @Autowired
  private CompanyService companyService;

  @PostMapping("findUnValidate")
  @ApiModelProperty("获取未审核公司--分页")
  public Result findUnValidate(@RequestBody CompanyQuery query) {
    query.setValidateStatus(CompanyValidateEnum.UNVALIDATE.getCode());
    return success(companyService.findUnValidate(query));
  }

  @PostMapping("find")
  @ApiModelProperty("获取公司--分页")
  public Result find(@RequestBody CompanyQuery query){
    return success(companyService.find(query));
  }
}
