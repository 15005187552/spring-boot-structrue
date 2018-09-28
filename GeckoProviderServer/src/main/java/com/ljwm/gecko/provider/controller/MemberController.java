package com.ljwm.gecko.provider.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.provider.service.ProviderMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@Api(tags = "会员管理")
public class MemberController extends BaseController {

  @Autowired
  private ProviderMemberService providerMemberService;

  @GetMapping("findMemberById")
  @ApiOperation("根据会员id查询会员信息")
  public Result<MemberVo> findMemberById(@PathVariable Long memberId){
    return success(providerMemberService.findMemberById(memberId));
  }
}
