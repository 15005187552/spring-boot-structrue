package com.ljwm.gecko.provider.controller;


import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.provider.model.form.LoginForm;
import com.ljwm.gecko.provider.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Api(tags = "权限登陆 API")
public class AuthController extends BaseController {

  @Autowired
  private AuthService authService;

  private static final String loginType = "PROVIDER";

  @PostMapping("login")
  @ApiOperation("登录接口")
  public Result login(@RequestBody LoginForm loginForm) {
    return success(authService.login(loginForm));
  }

  @GetMapping("me")
  @ApiOperation("获取me")
  public Result me() {
    return success(authService.me(SecurityKit.currentUser()));
  }

}
