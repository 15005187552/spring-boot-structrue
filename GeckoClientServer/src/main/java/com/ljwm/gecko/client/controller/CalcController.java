package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.CalcForm;
import com.ljwm.gecko.client.service.CalcService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/9/7 9:39
 */

@RestController
@Api(tags = "个税计算相关 API")
public class CalcController {

  @Autowired
  CalcService calcService;

  public Result calc(@RequestBody @Valid CalcForm calcForm){
    return calcService.calc(calcForm);
  }


}
