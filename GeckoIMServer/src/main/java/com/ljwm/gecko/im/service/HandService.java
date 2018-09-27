package com.ljwm.gecko.im.service;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.ljwm.gecko.base.service.MessageService;
import com.ljwm.gecko.im.ws.Const;
import com.ljwm.gecko.im.ws.ShowcaseWsMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsResponse;

import java.util.Optional;

/**
 * PushService  Created by yunqisong on 2018/9/24.
 * FOR: TODO:
 */

@Slf4j
@SuppressWarnings("ALL")
@Component
public class HandService {


  /**
   * 处理接收 TOPIC_PUSH_TO_ADMIN 主题的消息
   */
  @KafkaListener(topics = MessageService.TOPIC_PUSH_TO_ADMIN)
  public void handPushAdmin() {
    log.info("kkkkkkk:{}");

  }


  @KafkaListener(topics = MessageService.TOPIC_PUSH_FROM_ADMIN)
  public void handFromAdmin(ConsumerRecord<?, ?> record) {

    Optional<?> kafkaMessage = Optional.ofNullable(record.value());

    if (kafkaMessage.isPresent()) {
      JSON json = kafkaMessage.map(param -> {
        return JSONUtil.parse(param);
      }).get();
      JSON jsons = (JSON) json.getByPath("message");
      Integer id = (Integer) jsons.getByPath("recevierId");
      String text = (String)jsons.getByPath("message");


      ChannelContext channelContext = ShowcaseWsMsgHandler.CHANNEL_CONTEXT_MAP.get(id.toString());

      String msg = "{name:'" + id+ "',message:'" + text + "'}";

      WsResponse wsResponse = WsResponse.fromText(msg, "utf-8");
      Tio.send(channelContext,wsResponse);
//      Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);

      Object message = kafkaMessage.get();


      log.info("----------------- record =" + record);
      log.info("------------------ message =" + message);
    }
  }
}

