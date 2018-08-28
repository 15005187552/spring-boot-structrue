package com.ljwm.gecko.admin.controller;


import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.admin.model.form.LoginForm;
import com.ljwm.gecko.admin.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Api(tags = "权限登陆 API")
public class AuthController extends BaseController{

  @Autowired
  private AuthService authService;

  private static final String loginType = "ADMIN";

  @PostMapping("login")
  @ApiOperation("登录接口")
  public Result login(@RequestBody LoginForm loginForm) {

    LoginInfoHolder.setLoginType(loginType);

    return success(authService.login(loginForm));
  }

}
