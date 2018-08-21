package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuzhou on 2018/8/21.
 */
@Slf4j
@RestController
@RequestMapping("auth")
@Api(tags = "权限登录 API")
public class AuthController extends BaseController {

  @GetMapping("guest")
  @ApiOperation("请求以游客身份访问")
  public Result guest() {
    return success();
  }

  @GetMapping("me")
  @ApiOperation("当前用户信息")
  public Result me() {
    return success();
  }
}
