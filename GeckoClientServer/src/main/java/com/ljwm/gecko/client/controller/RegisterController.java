package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.service.RegisterService;
import com.ljwm.gecko.client.security.JwtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/8/28 17:50
 */
@Slf4j
@RestController
@Api(tags = "注册 API")
public class RegisterController extends BaseController {

  @Autowired
  RegisterService registerService;

  @PostMapping("/sendSMS")
  @ApiOperation("发送验证码")
  public Result sendSMS(@RequestBody @Valid RegisterForm registerForm,
                         HttpServletRequest request){
      return registerService.getSMS(registerForm, request);
  }

  @PostMapping("/registerWX")
  @ApiOperation("小程序注册成为会员")
  public Result registerWX(@RequestBody @Valid RegisterMemberForm registerMemberForm){
    return registerService.register(registerMemberForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/setPasswordWX")
  @ApiOperation("小程序设置或修改密码（短信验证码方式）")
  public Result setPasswordWX(@RequestBody @Valid PasswordForm passwordForm){
    return registerService.setPasswordWX(passwordForm);
  }

  @PostMapping("/registerPC")
  @ApiOperation("pc成为会员")
  public Result registerPC(@RequestBody @Valid RegisterPCForm registerPCForm){
    return registerService.registerPC(registerPCForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/modifyPassword")
  @ApiOperation("修改密码")
  public Result modifyPassword(@RequestBody @Valid ModifyPasswordForm modifyPasswordForm){
    return registerService.modifyPassword(modifyPasswordForm);
  }
}
