package com.ljwm.gecko.im.controller;

import cn.hutool.core.lang.Assert;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.im.service.ChatMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("chat")
@Api(tags = "聊天管理 API")
public class ChatController extends BaseController {

  public static List invokeMethod = new ArrayList();

  @Autowired
  private ChatMessageService chatMessageService;

  @GetMapping("getInvokeMethod")
  public Result getInvokeMethod() {
    return success(invokeMethod);
  }

  @GetMapping("providerAccess/{receiverId}/{providerId}")
  @ApiOperation("服务商客服接入")
  public Result providerAccess(@PathVariable Long receiverId, @PathVariable Long providerId) {
    chatMessageService.providerAccess(receiverId, providerId);
    return success();
  }
}
