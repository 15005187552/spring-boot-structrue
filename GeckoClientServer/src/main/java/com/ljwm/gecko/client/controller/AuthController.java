package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.client.model.dto.FormIdForm;
import com.ljwm.gecko.client.model.dto.GuestForm;
import com.ljwm.gecko.client.model.dto.LoginForm;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
  @ApiOperation("小程序登录")
  public Result login(@RequestBody GuestForm guestForm) {
    return success(authService.login(guestForm));
  }

  @PostMapping("/loginSys")
  @ApiOperation("登录")
  public Result loginSys(@RequestBody LoginForm loginForm) {
    return Result.success(authService.loginSys(loginForm));
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @GetMapping("me")
  @ApiOperation("当前用户信息")
  public Result me() {
    return success(authService.me(SecurityKit.currentUser()));
  }

  @PostMapping("getFormId")
  @ApiOperation("获取formId")
  public Result getFormId(@RequestBody @Valid FormIdForm formIdForm){
    return success(authService.uploadFormId(formIdForm));
  }
}
