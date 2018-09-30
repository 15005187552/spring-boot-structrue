package com.ljwm.gecko.im.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.im.model.form.SendProviderForm;
import com.ljwm.gecko.im.service.SessionDistributeService;
import com.ljwm.gecko.im.service.SessionService;
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
  private SessionService sessionService;

  @GetMapping("getInvokeMethod")
  public Result getInvokeMethod() {
    return success(invokeMethod);
  }

  @GetMapping("providerAccess/{receiverId}/{providerId}")
  @ApiOperation("服务商客服接入")
  public Result providerAccess(@PathVariable Long receiverId,@PathVariable Long providerId) {
    sessionService.providerAccess(receiverId,providerId);
    return success();
  }

  @PostMapping("sendToCustomer")
  @ApiOperation("服务商会话发送给用户 例：订单的接口推送到会话")
  public Result sendToCustomer(SendProviderForm form) {
    sessionService.sendToCustomer(form);
    return success();
  }
}
