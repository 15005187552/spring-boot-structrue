package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.service.EnumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Janiffy
 * @date 2018/8/24 16:50
 */
@Slf4j
@RestController
@RequestMapping("/enum")
@Api(tags = "枚举数据 API")
public class EnumController extends BaseController {
  @Autowired
  EnumService enumService;

  @PostMapping("/country")
  @ApiOperation("国籍")
  public Result country(){
    return enumService.country();
  }


  @PostMapping("/company")
  @ApiOperation("公司性质")
  public Result company(){
    return enumService.company();
  }

  @PostMapping("/certificate")
  @ApiOperation("身份类别")
  public Result certificate(){
    return enumService.certificate();
  }

  @PostMapping("/declare")
  @ApiOperation("申报类型")
  public Result declare(){
    return enumService.declare();
  }
}
