package com.ljwm.gecko.im.service;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.mapper.SocketInfoMapper;
import com.ljwm.gecko.base.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

import java.util.Objects;
import java.util.Optional;

/**
 * PushService  Created by yunqisong on 2018/9/24.
 * FOR: TODO:
 */

@Slf4j
@SuppressWarnings("ALL")
@Component
public class HandService {


  @Autowired
  private SocketInfoMapper socketInfoMapper;

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
      String text = (String) jsons.getByPath("message");


//      ChannelContext channelContext = ShowcaseWsMsgHandler.CHANNEL_CONTEXT_MAP.get(id.toString());

      String msg = "{name:'" + id + "',message:'" + text + "'}";

      WsResponse wsResponse = WsResponse.fromText(msg, "utf-8");
//      Tio.send(channelContext, wsResponse);
//      Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);

      Object message = kafkaMessage.get();


      log.info("----------------- record =" + record);
      log.info("------------------ message =" + message);
    }
  }

  @KafkaListener(topics = MessageService.TOPIC_SESSION_CUSTOMER)
  public void handSessionCustomer(ConsumerRecord<?, ?> record) {
    JSON json =
      Optional.of(record.value()).map(item -> {
        return JSONUtil.parse(item);
      }).get();
    JSON jsons = (JSON) json.getByPath("message");
    String text = (String) jsons.getByPath("message");
    Integer id = (Integer) jsons.getByPath("recevierId");
//    SocketInfo socketInfo = socketInfoMapper.selectById(id);
//    if (Objects.isNull(socketInfo))
//      throw new LogicException(ResultEnum.DATA_ERROR, "当前接收用户未登陆" + id);
//    Tio.send(Tio.getChannelContextsByUserid(s,id.toString()),)
//    Tio.getChannelContextsByUserid(Const.GROUP_ID,id);
  }
}

