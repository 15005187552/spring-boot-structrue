package com.ljwm.gecko.client.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 2018-10-10.
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试 API")
@Slf4j
public class TestController {
//  @Autowired
//  private MPTemplateService mpTemplateService;
//  @GetMapping("/index")
//  @ApiOperation("测试模版")
//  public Result test(){
//    Kv kv = Kv.create()
//      .set("keyword1","222")
//      .set("keyword2", DateUtil.formatDate(new Date()))
//      .set("keyword3","待付款")
//      .set("keyword4","4.00")
//      .set("keyword5","iphone8  *1")
//      .set("keyword6","");
//    return Result.success(mpTemplateService.send(27L, MPTemplateEnum.ORDER_STATUS, kv));
//  }
}
