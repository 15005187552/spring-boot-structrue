package com.ljwm.gecko.im.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.CustomerMessage;
import com.ljwm.gecko.base.entity.CustomerSession;
import com.ljwm.gecko.base.entity.ProviderUser;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.TableNameEnum;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.packet.Packet;
import com.ljwm.gecko.base.service.MessageService;
import com.ljwm.gecko.im.annotations.DynamicAnnotation;
import com.ljwm.gecko.im.enums.ChatSessionEnum;
import com.ljwm.gecko.im.enums.CustomerEnum;
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
 * tio 分发 客户入口 调用kafka
 */
@Slf4j
@Service
@SuppressWarnings("unchecked")
public class SessionDistributeService extends MessageService implements IDynamicAble {

  @Autowired
  private SessionDistributeService sessionDistributeService;

  @Autowired
  private CustomerMessageMapper customerMessageMapper;

  @Autowired
  private CustomerSessionMapper customerSessionMapper;

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

    Long providerId = jsonObject.getJSONObject("sessionBean").getLong("providerId");

    String text = jsonObject.getString("text");

    // 2.创建获取更新会话
    CustomerSession customerSession = sessionDistributeService.createOrUpdateSessiom(sessionId,providerId,memberId,guestId,null);

    // 3. 获取服务商下的所有用户
    List<ProviderUser> providerUsers = providerUserMapper.selectByMap(Kv.by("PROVIDER_ID",Optional.of(providerId).get()));
    List<Long> memberIds = providerUsers.stream().map(ProviderUser::getMemberId).collect(Collectors.toList());

    // 4.发送信息个所有服务商下的用户，并存储消息
    for (Long proUserId : memberIds) {

      // todo 切换 获取当前用户登陆在那个客户端
      // 4.1存储信息
      CustomerMessage customerMessage = sessionDistributeService.insertCustomerMessage(proUserId,text,customerSession,CustomerEnum.MEMBER);

      // 4.2 验证是否在线
      List<SocketInfo> socketInfo = getSocketInfos(proUserId);

      // 4.3 用户在线
      sessionDistributeService.sendMessage(customerMessage,socketInfo,TOPIC_TO_PROVIDER);
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
    assert !(sessionId != null && !Objects.equals(customerSessionMapper.selectById(sessionId).getReceptionistMemberId(),receiverId)) : "当前会话已有客户绑定请先获取权限";

    // 3.创建获取更新会话
    CustomerSession customerSession = sessionDistributeService.createOrUpdateSessiom(sessionId,providerId,memberId,guestId,receiverId);

    // 4.
    CustomerMessage customerMessage =
      sessionDistributeService.insertCustomerMessage(receiverId,text,customerSession,guestId == null ? CustomerEnum.MEMBER : CustomerEnum.GUEST);

    // 5.
    List<SocketInfo> socketInfo = getSocketInfos(receiverId);

    sessionDistributeService.sendMessage(customerMessage,socketInfo,TOPIC_TO_MEMBER);
  }

  @Transactional
  public void sendMessage(CustomerMessage customerMessage,List<SocketInfo> socketInfo,String topicToMember) {
    if (socketInfo.size() > 0) {
      // 4.3.1 发送kafka
      kafkaTemplate.send(topicToMember,JSON.toJSONString(Packet.create(topicToMember,customerMessage)));
      log.info("Send To Kafka Message Queue Successfully !");

      // 4.3.2 修改message状态
      customerMessageMapper.updateById(customerMessage.setStatus(1).setPushTime(DateTime.now()));
      log.info("Message:{} 状态修改成功",customerMessage.getId());
    }
  }

  @Transactional
  public CustomerSession createOrUpdateSessiom(Long sessionId,Long providerId,Long memberId,Long guestId,Long receiverId) {
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

    commonService.insertOrUpdate(customerSession,customerSessionMapper);
    log.info("会话：{} 创建或更新成功",customerSession.getId());
    return customerSession;
  }

  @Transactional
  public CustomerMessage insertCustomerMessage(Long receiverId,String text,CustomerSession customerSession,CustomerEnum customerEnum) {
    CustomerMessage customerMessage = new CustomerMessage();
    customerMessage.setCustomerSessionId(customerSession.getId())
      .setText(text)
      .setCreateTime(DateTime.now())
      .setReceiverType(customerEnum.getCode()) //todo 枚举
      .setReceiverId(receiverId)
      .setStatus(0); //todo 枚举

    customerMessageMapper.insert(customerMessage);
    return customerMessage;
  }

  public List<SocketInfo> getSocketInfos(Long id) {
    return socketInfoMapper.selectList(
      new QueryWrapper<SocketInfo>()
        .eq(SocketInfo.TARGET_ID,id)
        .and(wrapper -> wrapper.eq(SocketInfo.CHANNEL,LoginType.WX_APP.getCode()))
        .or().eq(SocketInfo.CHANNEL,LoginType.MOBILE.getCode())
    );
  }
}
