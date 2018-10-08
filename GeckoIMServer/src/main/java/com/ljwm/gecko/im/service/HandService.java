package com.ljwm.gecko.im.service;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.kit.SpringKit;
import com.ljwm.bootbase.kit.UtilKit;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.mapper.SocketInfoMapper;
import com.ljwm.gecko.base.service.MessageService;
import com.ljwm.gecko.im.ws.ShowcaseWsMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.SetUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;
import sun.reflect.misc.MethodUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

      WsResponse wsResponse = WsResponse.fromText(msg,"utf-8");
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
    WsResponse wsResponse = WsResponse.fromText(text,"utf-8");
    Set<ChannelContext> channelContexts = Tio.getChannelContextsByUserid(ShowcaseWsMsgHandler.GROUP_CONTEXT,id.toString()).getObj();

    Tio.send(channelContexts.stream().collect(Collectors.toList()).get(0),wsResponse);
  }

  @KafkaListener(topics = MessageService.TOPIC_TO_PROVIDER)
  public void handToProvider(ConsumerRecord<?, ?> record) {
    JSONObject message = getMesage(record);

    tioSendHandle(message,message.getString("receiverId"));
  }


  @KafkaListener(topics = MessageService.TOPIC_TO_MEMBER)
  public void HandToMember(ConsumerRecord<?, ?> record) {
    JSONObject message = getMesage(record);
    tioSendHandle(message,message.getString("receiverId"));
  }

  /**
   * 解析监听数据
   * @param record
   * @return
   */
  private JSONObject getMesage(ConsumerRecord<?, ?> record) {
    return Optional.of(record.value())
      .map(item -> JSONObject.parseObject(item.toString())).get()
      .getJSONObject("message");
  }

  /**
   * tio 分发
   * @param message
   * @param id
   */
  private void tioSendHandle(JSONObject message,String id) {
    WsResponse wsResponse = WsResponse.fromText(message.toJSONString(),"utf-8");
    Set<ChannelContext> channelContexts = Tio.getChannelContextsByUserid(ShowcaseWsMsgHandler.GROUP_CONTEXT,id).getObj();
    for (ChannelContext channelContext : channelContexts) {
      Tio.send(channelContext,wsResponse);
    }
  }
}

