package com.ljwm.gecko.provider.message;

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
import com.ljwm.gecko.base.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings("all")
public class PMessageService extends MessageService {
  @Autowired
  private CommonService commonService;

  @Autowired
  private SocketInfoMapper socketInfoMapper;

  @Autowired
  private PushMessageMapper pushMessageMapper;

  @Autowired
  private KafkaTemplate kafkaTemplate;

  @Transactional
  public void pPushMessage(List<Long> ids, String message) {
    DateTime now = DateTime.now();

    log.info("Push Time : {}", now);

    log.info("Check Target Socket Status, Target Ids : {}", ids.toArray().toString());

    List<SocketInfo> socketInfo
      = socketInfoMapper.selectList(
      new QueryWrapper<SocketInfo>()
        .eq(SocketInfo.TARGET_TABLE, "t_member")
        .eq(SocketInfo.TARGET_ID, ids) // todo : 集合？
        .eq(SocketInfo.STATUS, SocketStatusEnum.ON_LINE.getCode())
    );

    log.info("OnLine Count : {}", socketInfo.size());

    for (Long id : ids) {
      PushMessage pushMessage
        = new PushMessage()
        .setType(1)                         // TODO: 根据业务逻辑写枚举进行划分
        .setCreateTime(now)                 // 创建时间
        .setRecevierId(id)             // 接受者ID
        .setMessage(message)                // 消息 可以自己去拓展标题字段
        ;

      commonService.insertOrUpdate(pushMessage, pushMessageMapper);

      log.info("PushMessage : {}", pushMessage);
      kafkaTemplate.send(TOPIC_SESSION_CUSTOMER, JSON.toJSONString(Packet.create(TOPIC_SESSION_CUSTOMER, pushMessage)));
      if (socketInfo.size() > 0) {

        log.info("Send To Kafka Message Queue Successfully !");
      }
    }
  }

//  public void chant()
}
