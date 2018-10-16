package com.ljwm.gecko.im.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("push")
@Api(tags = "推送管理 API")
public class PushController {

//  @Autowired
//  private MessageService messageService;
//
//  @PostMapping("test")
//  public void test(@RequestBody MessageDto messageDto) {
//    List a = new ArrayList<>();
//    a.add(LoginType.WX_APP);
//    messageDto.setLoginType(a);
//    messageService.pushMessage(messageDto);
//  }
}
