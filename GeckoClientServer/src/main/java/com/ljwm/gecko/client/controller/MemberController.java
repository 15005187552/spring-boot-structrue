package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
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
@PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
public class MemberController extends BaseController {

  @Autowired
  private MemberInfoService memberInfoService;

  @Autowired
  CompanyUserService companyUserService;

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("company")
  @ApiOperation("会员所在企业")
  public Result myCompany() {
    return companyUserService.findCompany();
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("validateMember")
  @ApiOperation("会员认证")
  public Result validateMember(@RequestBody @Valid MemberDto memberDto){
    memberDto.setId(SecurityKit.currentId());
    memberInfoService.validateMember(memberDto);
    return success();
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @GetMapping("findMemberVoByRegMobile/{regMobile}")
  @ApiOperation("根据手机号查询资质认证人信息")
  public Result<MemberVo> findMemberVoByRegMobile(@PathVariable String regMobile){
    return success(memberInfoService.findMemberVoByRegMobile(regMobile));
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @GetMapping("findMemberVoByMemberId")
  @ApiOperation("查询当前登录会员认证信息")
  public Result<MemberVo> findMemberVoByMemberId(){
    return success(memberInfoService.findMemberVoByMemberId(SecurityKit.currentId()));
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/findMemberByMobile/{regMobile}")
  public Result findMemberByMobile(@PathVariable String regMobile){
    return success(memberInfoService.findMemberByMobile(regMobile));
  }

}
