package com.ljwm.gecko.im.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: xixil
 * Date: 2018/9/30 13:44
 * RUA
 */
@RestController
@RequestMapping("customer")
@Api("用户信息 API")
public class CustomerController extends BaseController{

  @GetMapping("findMessage/{memberId}")
  public Result findMessage(@PathVariable Long memberId){
    return success();
  }

  @GetMapping("findAllMessage/{memberId}/{providerId}")
  public Result findAllMessage(@PathVariable Long memberId,@PathVariable Long providerId) {
    return success();
  }
}
