package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.MemberComForm;
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

}
