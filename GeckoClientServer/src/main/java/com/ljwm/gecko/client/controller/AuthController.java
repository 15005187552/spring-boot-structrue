package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.GuestForm;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yuzhou on 2018/8/21.
 */
@Slf4j
@RestController
@RequestMapping("auth")
@Api(tags = "权限登录 API")
public class AuthController extends BaseController {

  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  @ApiOperation("登录")
  public Result guest(@RequestBody GuestForm guestForm) {
    return success(authService.login(guestForm));
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @GetMapping("me")
  @ApiOperation("当前用户信息")
  public Result me() {
    return authService.me();
  }
}
