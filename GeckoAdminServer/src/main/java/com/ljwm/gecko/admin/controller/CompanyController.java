package com.ljwm.gecko.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.gecko.admin.model.form.CompanyCheckForm;
import com.ljwm.gecko.admin.model.form.CompanyQuery;
import com.ljwm.gecko.admin.service.CompanyService;
import com.ljwm.gecko.base.enums.CompanyType;
import com.ljwm.gecko.base.enums.CompanyValidateEnum;
import com.ljwm.gecko.base.model.dto.AdminCompanyDto;
import com.ljwm.gecko.base.model.vo.UnValidateCompanyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ljwm.bootbase.dto.Result;

import java.util.stream.Collectors;

@RestController
@RequestMapping("company")
@Api(tags = "后管公司 API")
public class CompanyController extends BaseController {

  @Autowired
  private CompanyService companyService;

  @PostMapping("findUnValidate")
  @ApiOperation("获取未审核公司--分页")
  public Result<Page<UnValidateCompanyVo>> findUnValidate(@RequestBody CompanyQuery query) {
    query.setValidateStatus(CompanyValidateEnum.UN_VALIDATE.getCode());
    return success(companyService.findUnValidate(query));
  }

  @PostMapping("find")
  @ApiOperation("获取公司--分页")
  public Result<Page<AdminCompanyDto>> find(@RequestBody CompanyQuery query) {
    return success(companyService.find(query));
  }

  @GetMapping("getCompanyType")
  @ApiOperation("获取公司类型")
  public Result getCompanyType() {
    return success(EnumUtils.getEnumList(CompanyType.class).stream().map(i -> {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("code", i.getCode());
      jsonObject.put("name", i.getName());
      return jsonObject;
    }).collect(Collectors.toList()));
  }

  @PostMapping("checkCompany")
  @ApiOperation("审核公司")
  public Result checkCompany(@RequestBody CompanyCheckForm form) {
    return success(companyService.checkCompany(form));
  }

  @GetMapping("getCompany")
  @ApiOperation("获取所有公司")
  public Result getCompany() {
    return success(companyService.getCompany());
  }

  @GetMapping("getOnlineCompany")
  @ApiOperation("获取所有认证通过的公司")
  public Result getOnlineCompany(){
    return success(companyService.getOnlineCompany());
  }
}
