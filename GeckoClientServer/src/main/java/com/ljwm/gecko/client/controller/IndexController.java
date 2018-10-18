package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.CalcForm;
import com.ljwm.gecko.client.model.dto.EvaluateForm;
import com.ljwm.gecko.client.model.dto.NoticeQuery;
import com.ljwm.gecko.client.service.CalcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/9/7 9:39
 */

@RestController
@Api(tags = "首页相关 API")
@RequestMapping("/index")
public class IndexController {

  @Autowired
  CalcService calcService;

  @PostMapping("/calc")
  @ApiOperation("个税计算器")
  public Result calc(@RequestBody @Valid CalcForm calcForm){
    return calcService.calc(calcForm);
  }

  @PostMapping("/redPackage")
  @ApiOperation("领红包")
  public Result redPackage(){
    return calcService.redPackage();
  }

  @PostMapping("/notice/find")
  @ApiOperation("公告查询")
  public Result find(@RequestBody NoticeQuery query) {
    return calcService.find(query);
  }

  @PostMapping("evaluateTax")
  @ApiOperation("个税评估")
  public Result evaluateTax(@RequestBody @Valid EvaluateForm evaluateForm){
    return calcService.evaluateTax(evaluateForm);
  }

}
