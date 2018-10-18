package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: xixil
 * Date: 2018/10/18 18:40
 * RUA
 */

@RestController
@RequestMapping("protocol")
public class ProtocolController extends BaseController{

  public Result save(){
    return success();
  }

  public Result find(){
    return success();
  }
}
