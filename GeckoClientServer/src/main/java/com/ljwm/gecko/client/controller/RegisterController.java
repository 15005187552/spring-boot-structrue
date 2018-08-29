package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.RegisterForm;
import com.ljwm.gecko.base.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Validated
@Api(tags = "注册 API")
public class RegisterController extends BaseController {

  @Autowired
  RegisterService registerService;

  @PostMapping("/sendSMS")
  @ApiOperation("发送验证码")
  public Result register(@RequestBody @Valid RegisterForm registerForm,
                         HttpServletRequest request){
      return success(registerService.getSMS(registerForm, request));
    }

}
