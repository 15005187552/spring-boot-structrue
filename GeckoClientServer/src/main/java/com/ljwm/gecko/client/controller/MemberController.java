package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.security.JwtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuzhou on 2018/8/23.
 */
@Slf4j
@RestController
@RequestMapping("member")
@Api(tags = "会员 API")
@PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
public class MemberController extends BaseController {

  @GetMapping("company")
  @ApiOperation("会员所在企业")
  public Result myCompany() {
    return success();
  }
}
