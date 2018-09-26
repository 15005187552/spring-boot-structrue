package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.MemberComForm;
import com.ljwm.gecko.client.model.dto.CompanyDto;
import com.ljwm.gecko.client.model.dto.InactiveForm;
import com.ljwm.gecko.client.model.dto.MemberForm;
import com.ljwm.gecko.client.model.dto.MemberIdDto;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.CompanyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/member")
@Api(tags = "会员企业 API")
public class CompanyUserController {

  @Autowired
  CompanyUserService companyUserService;

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/memberEnterCom")
  @ApiOperation("会员加入企业")
  public Result memberEnterCom(@RequestBody @Valid MemberComForm memberComForm){
    return companyUserService.memberEnterCom(memberComForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/inactiveCompany")
  @ApiOperation("已邀请你加入的公司列表（未激活的）")
  public Result inactiveCompany(@RequestBody MemberForm memberForm){
    return companyUserService.inactiveCompany(memberForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/enterCompany")
  @ApiOperation("加入公司")
  public Result enterCompany(@RequestBody InactiveForm inactiveForm){
    return companyUserService.enterCompany(inactiveForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/taxCompany")
  @ApiOperation("当前纳税的公司信息")
  public Result taxCompany(){
    return companyUserService.taxCompany();
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/roleInCompany")
  @ApiOperation("当前会员在公司的角色")
  public Result roleInCompany(@RequestBody CompanyDto companyDto){
    return companyUserService.roleInCompany(companyDto);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/memberRoleList")
  @ApiOperation("公司员工角色列表")
  public Result memberRoleList(@RequestBody CompanyDto companyDto){
    return companyUserService.memberRoleList(companyDto);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/memberRole")
  @ApiOperation("会员角色信息表")
  public Result memberRole(@RequestBody MemberIdDto memberIdDto){
    return companyUserService.memberRole(memberIdDto);
  }

}
