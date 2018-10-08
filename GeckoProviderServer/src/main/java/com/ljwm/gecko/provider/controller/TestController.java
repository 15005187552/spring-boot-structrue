package com.ljwm.gecko.provider.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.ljwm.gecko.provider.message.MessageBean;
import com.ljwm.gecko.provider.message.PMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

  @Autowired
  private PMessageService pMessageService;

  @GetMapping("send/{id}")
  public void send(Long id){
    MessageBean messageBean = new MessageBean();
    messageBean.setFromId(-1L).setCreateTime(DateUtil.date()).setName("Rua").setMessage("gggggg").setSubject("Test");
    List<Long> as = new ArrayList<>();
    as.add(id);
    pMessageService.pPushMessage(as, JSONUtil.toJsonStr(messageBean));
  }
}
