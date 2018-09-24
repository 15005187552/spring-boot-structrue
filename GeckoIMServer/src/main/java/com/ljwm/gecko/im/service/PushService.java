package com.ljwm.gecko.im.service;

import com.ljwm.gecko.base.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.tio.core.GroupContext;
import org.tio.core.Tio;

/**
 * PushService  Created by yunqisong on 2018/9/24.
 * FOR: TODO:
 */

@Slf4j
@SuppressWarnings("ALL")
public class PushService {


  /**
   * 处理接收 TOPIC_PUSH_TO_ADMIN 主题的消息
   */
  @KafkaListener(topics = MessageService.TOPIC_PUSH_TO_ADMIN)
  public void handPushAdmin() {


  }
}

