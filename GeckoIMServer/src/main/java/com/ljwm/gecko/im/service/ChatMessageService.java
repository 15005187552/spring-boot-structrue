package com.ljwm.gecko.im.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.CustomerMessage;
import com.ljwm.gecko.base.entity.CustomerSession;
import com.ljwm.gecko.base.entity.ProviderUser;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.enums.TableNameEnum;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.packet.Packet;
import com.ljwm.gecko.base.service.MessageService;
import com.ljwm.gecko.im.annotations.DynamicAnnotation;
import com.ljwm.gecko.im.enums.ChatSessionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * tio 客户入口 调用kafka
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class ChatMessageService extends MessageService implements IDynamicAble {

  @Autowired
  private CustomerMessageMapper customerMessageMapper;

  @Autowired
  private CustomerSessionMapper customerSessionMapper;

  @Autowired
  private ChatMessageService chatMessageService;

  @Autowired
  private ProviderUserMapper providerUserMapper;

  @Autowired
  private SocketInfoMapper socketInfoMapper;

  @Autowired
  private CommonService commonService;

  @Autowired
  private KafkaTemplate kafkaTemplate;

  @Transactional
  @DynamicAnnotation(code = "1", desc = "聊天发送给服务商")
  public void chantSendToProvider(String message) {
    // 1.获取数据
    JSONObject jsonObject = JSONObject.parseObject(message);

    Long sessionId = jsonObject.getLong("sessionId");

    Long memberId = jsonObject.getLong("memberId");

    Long guestId = jsonObject.getLong("guestId");

    Long providerId = jsonObject.getLong("providerId");

    String text = jsonObject.getString("text");

    // 2.创建获取更新会话
    CustomerSession customerSession = chatMessageService.createOrUpdateSessiom(sessionId, providerId, memberId, guestId, null);

    // 3. 获取服务商下的所有用户
    List<ProviderUser> providerUsers = providerUserMapper.selectByMap(Kv.by("PROVIDER_ID", Optional.of(providerId).get()));
    List<Long> memberIds = providerUsers.stream().map(ProviderUser::getMemberId).collect(Collectors.toList());

    // 4.发送信息个所有服务商下的用户，并存储消息
    for (Long proUserId : memberIds) {
      // 4.1存储信息
      CustomerMessage customerMessage = new CustomerMessage();
      customerMessage.setCustomerSessionId(customerSession.getId())
        .setText(text)
        .setCreateTime(DateTime.now())
        .setReceiverType(2) //todo 枚举
        .setReceiverId(proUserId)
        .setStatus(0); //todo 枚举

      customerMessageMapper.insert(customerMessage);

      // 4.2 验证是否在线
      List<SocketInfo> socketInfo
        = socketInfoMapper.selectList(
        new QueryWrapper<SocketInfo>()
          .eq(SocketInfo.TARGET_TABLE, TableNameEnum.T_PROVIDER.getCode())
          .eq(SocketInfo.TARGET_ID, proUserId)
//          .eq(SocketInfo.STATUS, SocketStatusEnum.ON_LINE.getCode())
      );

      // 4.3 用户在线
      if (socketInfo.size() > 0) {
        // 4.3.1 发送kafka
        kafkaTemplate.send(TOPIC_TO_PROVIDER, JSON.toJSONString(Packet.create(TOPIC_TO_PROVIDER, customerMessage)));
        log.info("Send To Kafka Message Queue Successfully !");

        // 4.3.2 修改message状态
        customerMessageMapper.updateById(customerMessage.setStatus(1).setPushTime(DateTime.now()));
        log.info("Message:{} 状态修改成功", customerMessage.getId());
      }
    }
  }

  @Transactional
  @DynamicAnnotation(code = "2", desc = "聊天发送给用户")
  public void chatSendToMember(String message) {

    // 1.获取数据
    JSONObject jsonObject = JSONObject.parseObject(message);

    JSONObject sessionBean = jsonObject.getJSONObject("sessionBean");

    Long sessionId = sessionBean.getLong("sessionId");

    Long memberId = sessionBean.getLong("memberId");

    Long guestId = sessionBean.getLong("guestId");

    Long providerId = sessionBean.getLong("providerId");

    Long receiverId = jsonObject.getLong("receiverId");

    String text = jsonObject.getString("text");

    //2.判断当前客户是否拥有权限
    Assert.isTrue(sessionId != null && !Objects.isNull(customerSessionMapper.selectById(sessionId).getReceptionistMemberId()),
      "当前会话已有客户绑定请先获取权限");

    // 3.创建获取更新会话
    CustomerSession customerSession = chatMessageService.createOrUpdateSessiom(sessionId, providerId, memberId, guestId, receiverId);

    // 4.
    CustomerMessage customerMessage = new CustomerMessage();
    customerMessage.setCustomerSessionId(customerSession.getId())
      .setText(text)
      .setCreateTime(DateTime.now())
      .setReceiverType(2) //todo 枚举
      .setReceiverId(receiverId)
      .setStatus(0); //todo 枚举

    customerMessageMapper.insert(customerMessage);
  }

  @Transactional
  public CustomerSession createOrUpdateSessiom(Long sessionId, Long providerId, Long memberId, Long guestId, Long receiverId) {
    CustomerSession customerSession = null;
    if (sessionId != null)
      customerSession = customerSessionMapper.selectById(sessionId);
    if (customerSession == null)
      customerSession = new CustomerSession().setCreateTime(DateUtil.date());
    customerSession.setProviderId(providerId)
      .setStatus(ChatSessionEnum.ACTIVE.getCode())
      .setCustomerMemberId(memberId)
      .setCustomerGuestId(guestId)
      .setName("临时会话");
    if (receiverId != null) customerSession.setReceptionistMemberId(receiverId);

    commonService.insertOrUpdate(customerSession, customerSessionMapper);
    log.info("会话：{} 创建或更新成功", customerSession.getId());
    return customerSession;
  }

  @Autowired
  private MemberMapper memberMapper;

  @Transactional
  public void providerAccess(Long receiverId, Long sessionId) {

    log.debug("1.判错处理");
    log.debug("    1.1 用户是否存在");
    assert Objects.isNull(memberMapper.selectById(receiverId)) : "该用户" + receiverId + "不存在";

    log.debug("    1.2 会话是否存在");
    CustomerSession customerSession = customerSessionMapper.selectById(sessionId);
    assert Objects.isNull(customerSession) : "当前会话" + sessionId + "不存在";

    log.debug("    1.3 用户是否在该会话绑定的服务商下");
    List<ProviderUser> providerUsers = providerUserMapper.selectList(new QueryWrapper<ProviderUser>().eq("PROVIDER_ID", customerSession.getProviderId()));
    List<Long> memberIds = providerUsers.stream().map(ProviderUser::getMemberId).collect(Collectors.toList());
    assert !memberIds.contains(receiverId) : "当前用户" + receiverId + "不属于当前会话中的服务商";

    log.debug("2.更新会话，替换接待人");
    customerSession.setReceptionistMemberId(receiverId);
    customerSessionMapper.updateById(customerSession);
  }
}
