package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.MemberComForm;
import com.ljwm.gecko.client.model.dto.InactiveForm;
import com.ljwm.gecko.client.model.dto.MemberForm;
import com.ljwm.gecko.client.service.CompanyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/9/8 17:48
 */
@Slf4j
@RestController
@RequestMapping("member")
@Api(tags = "会员企业 API")
public class CompanyUserController {

  @Autowired
  CompanyUserService companyUserService;

  @PostMapping("/memberEnterCom")
  @ApiOperation("会员加入企业")
  public Result memberEnterCom(@RequestBody @Valid MemberComForm memberComForm){
    return companyUserService.memberEnterCom(memberComForm);
  }

  @PostMapping("/inactiveCompany")
  @ApiOperation("已邀请你加入的公司列表（未激活的）")
  public Result inactiveCompany(@RequestBody MemberForm memberForm){
    return companyUserService.inactiveCompany(memberForm);
  }

  @PostMapping("/enterCompany")
  @ApiOperation("加入公司")
  public Result enterCompany(@RequestBody InactiveForm inactiveForm){
    return companyUserService.enterCompany(inactiveForm);
  }

  @PostMapping("/taxCompany")
  @ApiOperation("当前纳税的公司信息")
  public Result taxCompany(){
    return companyUserService.taxCompany();
  }

}
