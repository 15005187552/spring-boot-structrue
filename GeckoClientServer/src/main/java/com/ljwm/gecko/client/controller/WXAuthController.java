package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.model.dto.LoginWithSignature;
import com.ljwm.gecko.base.model.vo.WxResultMe;
import com.ljwm.gecko.base.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/8/29 18:32
 */
@Slf4j
@RestController
@Api(tags = "微信登录 API")
public class WXAuthController extends BaseController {

  @Autowired
  UserService userService;

  @PostMapping("mpCode")
  @ApiOperation("前台--微信code登录(不授权)")
  public Result<WxResultMe> mpCode(@RequestBody @Valid LoginWithSignature loginWithSignature) {
    LoginInfoHolder.setLoginType(LoginType.WX_APP.getName());
    return success(userService.mpCode(loginWithSignature));
  }
}
