package com.ljwm.gecko.admin.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.ljwm.gecko.admin.message.AMessageService;
import com.ljwm.gecko.admin.message.MessageBean;
import com.ljwm.gecko.base.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  private AMessageService aMessageService;

  @Autowired
  private MessageService messageService;

  @GetMapping("imSend")
  public void imSend() {
    MessageBean messageBean = new MessageBean();
    messageBean.setFromId(-1L).setCreateTime(DateUtil.date()).setMessage("Hello").setSubject("Test");
    List<Long> ids = new ArrayList<>();
    ids.add(6L);
    ids.add(14L);
    ids.add(18L);
    aMessageService.adminPushMessage(ids, JSONUtil.toJsonStr(messageBean));
  }

  @GetMapping("kasend/{id}")
  public void kasend(@PathVariable Long id) {
    MessageBean messageBean = new MessageBean();
    messageBean.setFromId(-1L).setCreateTime(DateUtil.date()).setMessage("Hello").setSubject("Test");
    List<Long> ids = new ArrayList<>();
    ids.add(id);
    aMessageService.adminPushMessage(ids, JSONUtil.toJsonStr(messageBean));
  }

  @GetMapping("baseSend")
  public void baseSend(){
    MessageBean messageBean = new MessageBean();
    messageBean.setFromId(-1L).setCreateTime(DateUtil.date()).setMessage("Hello").setSubject("Test");
    messageService.pushMessageToAdmin(6L,JSONUtil.toJsonStr(messageBean));
  }

  @GetMapping("test")
  public void test(){
    aMessageService.pushTest();
  }
}