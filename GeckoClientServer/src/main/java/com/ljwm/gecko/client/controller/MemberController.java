package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.MemberDto;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.service.MemberInfoService;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.CompanyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by yuzhou on 2018/8/23.
 * Updated by Janiffy on 2018/9/8.
 */
@Slf4j
@RestController
@RequestMapping("member")
@Api(tags = "会员 API")
@PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
public class MemberController extends BaseController {

  @Autowired
  private MemberInfoService memberInfoService;

  @Autowired
  CompanyUserService companyUserService;

  @PostMapping("company")
  @ApiOperation("会员所在企业")
  public Result myCompany() {
    return companyUserService.findCompany();
  }

  @PostMapping("validateMember")
  @ApiOperation("会员认证")
  public Result validateMember(@RequestBody @Valid MemberDto memberDto){
    memberInfoService.validateMember(memberDto);
    return success();
  }

  @GetMapping("findMemberVoByRegMobile/{regMobile}")
  @ApiOperation("根据手机号查询资质认证人信息")
  public Result<MemberVo> findMemberVoByRegMobile(@PathVariable String regMobile){
    return success(memberInfoService.findMemberVoByRegMobile(regMobile));
  }



}
