package com.ljwm.gecko.im.kafka;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@SuppressWarnings("all")
public class KafkaSender {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;


  //发送消息方法
  public void send() {
    Message message = new Message();
    message.setId(System.currentTimeMillis());
    message.setMsg(RandomUtil.randomUUID());
    message.setSendTime(new Date());

    log.info("+++++++++++++++++++++  message = {}", JSONUtil.toJsonStr(message));
    kafkaTemplate.send("zhisheng", JSONUtil.toJsonStr(message));
  }
}
