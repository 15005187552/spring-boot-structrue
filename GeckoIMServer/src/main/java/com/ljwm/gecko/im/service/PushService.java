package com.ljwm.gecko.im.service;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.PushMessage;
import com.ljwm.gecko.base.entity.PushMessageType;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.mapper.PushMessageMapper;
import com.ljwm.gecko.base.mapper.PushMessageTypeMapper;
import com.ljwm.gecko.base.mapper.SocketInfoMapper;
import com.ljwm.gecko.base.model.dto.im.MessageDto;
import com.ljwm.gecko.base.packet.Packet;
import com.ljwm.gecko.base.service.MessageService;
import com.ljwm.gecko.im.model.dto.PushMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Author: xixil
 * Date: 2018/10/11 9:15
 * RUA
 */

@Slf4j
@Service
@SuppressWarnings("all")
public class PushService {

  @Autowired
  private PushMessageTypeMapper pushMessageTypeMapper;

  @Autowired
  private PushMessageMapper pushMessageMapper;

  @Autowired
  private PushService pushService;

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
