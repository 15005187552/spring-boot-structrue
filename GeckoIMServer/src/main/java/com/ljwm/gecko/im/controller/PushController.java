package com.ljwm.gecko.im.controller;

import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.model.dto.im.MessageDto;
import com.ljwm.gecko.base.service.MessageService;
import com.ljwm.gecko.im.service.PushService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("push")
@Api(tags = "推送管理 API")
public class PushController {

  @Autowired
  private MessageService messageService;

  @PostMapping("test")
  public void test(@RequestBody MessageDto messageDto) {
    List a = new ArrayList<>();
    a.add(LoginType.WX_APP);
    messageDto.setLoginType(a);
    messageService.pushMessage(messageDto);
  }
}
