package com.ljwm.gecko.base.service;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.PushMessage;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.enums.SocketStatusEnum;
import com.ljwm.gecko.base.mapper.PushMessageMapper;
import com.ljwm.gecko.base.mapper.SocketInfoMapper;
import com.ljwm.gecko.base.packet.Packet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

  public static final String TOPIC_CUSTOMER_MESSAGE = "TOPIC_CUSTOMER_MESSAGE";       // 客服消息Topic


  @Autowired
  private CommonService commonService;

  @Autowired
  private SocketInfoMapper socketInfoMapper;

  @Autowired
  private PushMessageMapper pushMessageMapper;


  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;


  /**
   * 给Adnmin推送消息
   *
   * @param adminId
   * @param message
   */
  @Transactional
  public void pushMessageToAdmin(Long adminId, String message) {

    DateTime now = DateTime.now();

    log.info("Push Time : {}", now);

    log.info("Check Admin Socket Status, Admin Id : {}", adminId);

    List<SocketInfo> socketInfo
      = socketInfoMapper.selectList(
      new QueryWrapper<SocketInfo>()
        .eq(SocketInfo.TARGET_TABLE, "t_admin")
        .eq(SocketInfo.TARGET_ID, adminId)
        .eq(SocketInfo.STATUS, SocketStatusEnum.ON_LINE.getCode())
    );

    log.info("OnLine Count : {}", socketInfo.size());
    PushMessage pushMessage
      = new PushMessage()
      .setType(1)                         // TODO: 根据业务逻辑写枚举进行划分
      .setCreateTime(now)                 // 创建时间
      .setMessage(message)                // 消息 可以自己去拓展标题字段
      .setRecevierId(adminId)             // 接受者ID
      ;

    commonService.insertOrUpdate(pushMessage, pushMessageMapper);

    log.info("PushMessage : {}", pushMessage);

    if (socketInfo.size() > 0) {

      kafkaTemplate.send(TOPIC_PUSH_TO_ADMIN, JSON.toJSONString(Packet.create(TOPIC_PUSH_TO_ADMIN, pushMessage)));

      log.info("Send To Kafka Message Queue Successfully !");
    }

  }
}

