package com.ljwm.gecko.im.service;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.gecko.base.entity.CustomerMessage;
import com.ljwm.gecko.base.entity.CustomerSession;
import com.ljwm.gecko.base.entity.ProviderUser;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.mapper.CustomerMessageMapper;
import com.ljwm.gecko.base.mapper.CustomerSessionMapper;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.mapper.ProviderUserMapper;
import com.ljwm.gecko.base.packet.Packet;
import com.ljwm.gecko.base.service.MessageService;
import com.ljwm.gecko.im.enums.CustomerEnum;
import com.ljwm.gecko.im.model.form.SendProviderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: xixil
 * Date: 2018/9/30 14:23
 * RUA
 */

@Slf4j
@Service
@SuppressWarnings("all")
public class SessionService {

  @Autowired
  private SessionDistributeService sessionDistributeService;

  @Autowired
  private CustomerMessageMapper customerMessageMapper;

  @Autowired
  private CustomerSessionMapper customerSessionMapper;

  @Autowired
  private ProviderUserMapper providerUserMapper;

  @Autowired
  private KafkaTemplate kafkaTemplate;

  @Autowired
  private MemberMapper memberMapper;

  @Transactional
  public void providerAccess(Long receiverId,Long sessionId) {

    log.debug("1.判错处理");
    log.debug("    1.1 用户是否存在");
    assert !Objects.isNull(memberMapper.selectById(receiverId)) : "该用户" + receiverId + "不存在";

    log.debug("    1.2 会话是否存在");
    CustomerSession customerSession = customerSessionMapper.selectById(sessionId);
    assert !Objects.isNull(customerSession) : "当前会话" + sessionId + "不存在";

    log.debug("    1.3 用户是否在该会话绑定的服务商下");
    List<ProviderUser> providerUsers = providerUserMapper.selectList(new QueryWrapper<ProviderUser>().eq("PROVIDER_ID",customerSession.getProviderId()));
    List<Long> memberIds = providerUsers.stream().map(ProviderUser::getMemberId).collect(Collectors.toList());
    assert memberIds.contains(receiverId) : "当前用户" + receiverId + "不属于当前会话中的服务商";

    log.debug("2.更新会话，替换接待人");
    customerSession.setReceptionistMemberId(receiverId);
    customerSessionMapper.updateById(customerSession);
  }

  @Transactional
  public void sendToCustomer(SendProviderForm form) {

    log.debug("1.获取会话");
    List<CustomerSession> infos = customerSessionMapper.selectList(
      new QueryWrapper<CustomerSession>()
        .eq(CustomerSession.CUSTOMER_MEMBER_ID,form.getMemberId())
        .eq(CustomerSession.PROVIDER_ID,form.getProviderId())
    );

    log.debug("2.判断会话是否存在");
    assert infos.size() > 0 : "未找到会话（session）";

    log.debug("3.插入新的消息");
    CustomerMessage customerMessage = sessionDistributeService.insertCustomerMessage(form.getMemberId(),form.getMessage(),infos.get(0),CustomerEnum.MEMBER);

    log.debug("4.获取用户上线数据");
    List<SocketInfo> socketInfos = sessionDistributeService.getSocketInfos(form.getMemberId());

    log.debug("5.在线则开始发送");
    if (socketInfos.size() > 0) {
      log.debug("    5.1 发送kafka");
      kafkaTemplate.send(MessageService.TOPIC_TO_MEMBER,JSON.toJSONString(Packet.create(MessageService.TOPIC_TO_MEMBER,customerMessage)));
      log.info("Send To Kafka Message Queue Successfully !");

      log.debug("    5.2 修改消息状态未已推送");
      customerMessageMapper.updateById(customerMessage.setStatus(1).setPushTime(DateTime.now()));
      log.info("Message:{} 状态修改成功",customerMessage.getId());
    }
  }
}
