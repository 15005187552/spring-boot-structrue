package com.ljwm.gecko.im.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.im.service.CustomerService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private CustomerService customerService;

  @GetMapping("findMessage")
  public Result findMessage(){
    return success(customerService.findMessage());
  }

  @GetMapping("findAllMessage/{memberId}/{providerId}")
  public Result findAllMessage(@PathVariable Long memberId,@PathVariable Long providerId) {
    return success();
  }
}
