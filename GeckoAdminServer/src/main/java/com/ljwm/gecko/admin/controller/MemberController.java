package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.model.dto.MemberConfirmDto;
import com.ljwm.gecko.base.model.dto.MemberQueryDto;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.service.MemberInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@Api(tags = "会员信息 API")
public class MemberController extends BaseController {

  @Autowired
  private MemberInfoService memberInfoService;

  @PostMapping("findByPage")
  @ApiOperation("查询会员列表---带分页")
  private Result<Page<MemberVo>> findByPage(@RequestBody MemberQueryDto memberQueryDto){
    return success(memberInfoService.findByPage(memberQueryDto));
  }

  @PostMapping("checkMember")
  @ApiModelProperty("会员审核")
  public Result checkMember(@RequestBody MemberConfirmDto memberConfirmDto){
    memberConfirmDto.setValidaterId(SecurityKit.currentId());
    memberInfoService.checkMember(memberConfirmDto);
    return success();
  }
}
