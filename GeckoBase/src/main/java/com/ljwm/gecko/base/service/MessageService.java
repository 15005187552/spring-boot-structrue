package com.ljwm.gecko.base.service;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.PushMessage;
import com.ljwm.gecko.base.entity.PushMessageType;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.SocketStatusEnum;
import com.ljwm.gecko.base.mapper.PushMessageMapper;
import com.ljwm.gecko.base.mapper.PushMessageTypeMapper;
import com.ljwm.gecko.base.mapper.SocketInfoMapper;
import com.ljwm.gecko.base.model.dto.im.MessageDto;
import com.ljwm.gecko.base.model.dto.im.PushMessageDto;
import com.ljwm.gecko.base.packet.Packet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * MessageService  Created by yunqisong on 2018/9/24.
 * FOR: 发送消息Service
 */

@Slf4j
@SuppressWarnings("ALL")
@Service
public class MessageService {


  public static final String TOPIC_PUSH_TO_ADMIN = "TOPIC_PUSH_TO_ADMIN";             // 推送消息给Admin

  public static final String TOPIC_PUSH_FROM_ADMIN = "TOPIC_PUSH_FROM_ADMIN";         // 从Admin推送消息

  public static final String TOPIC_SESSION_CUSTOMER = "TOPIC_SESSION_CUSTOMER";       // 用户会话聊天

  public static final String TOPIC_TO_PROVIDER = "TOPIC_TO_PROVIDER";       // 发送至服务商Topic

  public static final String TOPIC_TO_MEMBER = "TOPIC_TO_MEMBER";       // 发送至用户

  public static final String PUSH_MESSAGE = "PUSH_MESSAGE";       // 推送消息



  @Autowired
  private PushMessageTypeMapper pushMessageTypeMapper;

  @Autowired
  private PushMessageMapper pushMessageMapper;

  @Autowired
  private MessageService pushService;

  /**
   * 推送消息
   * @param messageDto
   */
  @Transactional
  public void pushMessage(MessageDto messageDto) {

    // 存储信息
    PushMessage pushMessage = new PushMessage();
    pushMessage.setMessage(messageDto.getMessage())
      .setRecevierId(messageDto.getReceiverId())
      .setCreateTime(DateTime.now());

    pushMessageMapper.insert(pushMessage);

    for (LoginType loginType : messageDto.getLoginType()) {
      // 存储 信息 接收端类型
      PushMessageType pushMessageType = new PushMessageType();
      pushMessageType.setPushMessageId(pushMessage.getId())
        .setType(loginType.getCode())
        .setStatus(0);

      pushMessageTypeMapper.insert(pushMessageType);

      // 查询在线状态
      List<SocketInfo> socketInfos = getSocketInfos(messageDto.getReceiverId(),loginType.getCode());

      if (Objects.equals(pushMessageType.getType(),LoginType.WX_APP.getCode()) ||
        socketInfos.size() > 0) {
        pushService.sendMessage(pushMessage,pushMessageType,MessageService.PUSH_MESSAGE);
      }
    }
  }

  @Autowired
  private KafkaTemplate kafkaTemplate;

  /**
   * kafka推送
   * @param pushMessage
   * @param pushMessageType
   * @param topic
   */
  @Transactional
  public void sendMessage(PushMessage pushMessage,PushMessageType pushMessageType,String topic) {
    kafkaTemplate.send(topic,JSON.toJSONString(
      Packet.create(topic,new PushMessageDto(pushMessageType,pushMessage))
    ));
    log.info("Send To Kafka Message Queue Successfully !");

    pushMessageTypeMapper.updateById(pushMessageType.setStatus(1).setPushTime(DateTime.now()));
    log.info("Message:{} 状态修改成功",pushMessage.getId());
  }

  @Autowired
  private SocketInfoMapper socketInfoMapper;

  /**
   * 获取在线状态
   * @param id
   * @param loginCode
   * @return
   */
  public List<SocketInfo> getSocketInfos(Long id,Integer loginCode) {
    return socketInfoMapper.selectList(
      new QueryWrapper<SocketInfo>()
        .eq(SocketInfo.CHANNEL,loginCode)
        .eq(SocketInfo.TARGET_ID,id)
    );
  }
}

