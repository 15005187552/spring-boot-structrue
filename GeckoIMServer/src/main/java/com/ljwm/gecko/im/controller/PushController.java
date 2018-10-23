package com.ljwm.gecko.im.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.MPTemplateEnum;
import com.ljwm.gecko.base.model.dto.im.MessageDto;
import com.ljwm.gecko.base.service.MessageService;
import com.ljwm.gecko.im.service.MPTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("push")
@Api(tags = "推送管理 API")
public class PushController {

  @Autowired
  private MessageService messageService;

  @PostMapping("test")
  public void test(@RequestBody MessageDto messageDto) {
    List a = new ArrayList();
    a.add(LoginType.WX_APP);
    messageDto.setLoginType(a);
    messageService.pushMessage(messageDto);
  }

  @Autowired
  private MPTemplateService mpTemplateService;
  @GetMapping("/index")
  @ApiOperation("测试模版")
  public Result test(){
    Kv kv = Kv.create()
      .set("keyword1","222")
      .set("keyword2", DateUtil.formatDate(new Date()))
      .set("keyword3","待付款");
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("keyword1","3333");
    jsonObject.put("keyword2",DateTime.now());
    jsonObject.put("keyword3","待付款");
    return Result.success(mpTemplateService.sendSimple(27L,MPTemplateEnum.AUDIT.getTemplateId(),jsonObject));
//    return Result.success(mpTemplateService.send(27L, MPTemplateEnum.AUDIT, kv));
  }
}
