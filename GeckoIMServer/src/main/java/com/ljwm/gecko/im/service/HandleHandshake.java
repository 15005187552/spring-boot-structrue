package com.ljwm.gecko.im.service;

import cn.hutool.core.date.DateUtil;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.base.entity.CustomerSession;
import com.ljwm.gecko.base.entity.PushMessage;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.enums.SocketChannelEnum;
import com.ljwm.gecko.base.mapper.CustomerSessionMapper;
import com.ljwm.gecko.base.mapper.PushMessageMapper;
import com.ljwm.gecko.base.mapper.SocketInfoMapper;
import com.ljwm.gecko.base.service.MessageService;
import com.ljwm.gecko.im.ws.ShowcaseWsMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.utils.TioUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@SuppressWarnings("all")
public class HandleHandshake {

  @Autowired
  private HandleHandshake handleHandshake;

  @Autowired
  private SocketInfoMapper socketInfoMapper;

  @Autowired
  private PushMessageMapper pushMessageMapper;

  @Autowired
  private CustomerSessionMapper customerSessionMapper;

  @Transactional
  public void connectHandle(String id, String ip, SocketChannelEnum socketChannelEnum) {
    // 处理异常socket
    SocketInfo socketInfo = null;
    socketInfo = socketInfoMapper.selectById(id);
    if (Objects.isNull(socketInfo)) {
      log.info("处理{}异常状态为：登出异常", id);
      socketInfoMapper.deleteById(id);
    }
    // 存储上线状态
    socketInfo = new SocketInfo();
    socketInfo
      .setTargetTable(socketChannelEnum.getTableCode())
      .setChannel(socketChannelEnum.getCode())
      .setConnectTime(DateUtil.date())
      .setTargetId(Long.valueOf(id))
      .setStatus(0) //todo 枚举状态 获取删除该字段
      .setIp(ip);
    socketInfoMapper.insert(socketInfo);
  }

  @Transactional
  public void connetCloseHanle(String wsCode) {

    List<SocketInfo> socketInfos = socketInfoMapper.selectByMap(Kv.by("CONNECT_CONTENXT_ID", wsCode));
    if (socketInfos.size() <= 0) throw new LogicException(ResultEnum.DATA_ERROR, "无法获取到socketInfo");
    log.info("连接登出操作", wsCode);
    socketInfoMapper.deleteByMap(Kv.by("CONNECT_CONTENXT_ID", wsCode));

////    Tio.remove(ShowcaseWsMsgHandler.CHANNEL_CONTEXT_MAP.get(wsCode),"remove");
//

//    for (ChannelContext channelContext : Tio.getAllChannelContexts(ShowcaseWsMsgHandler.CHANNEL_CONTEXT_MAP.get(wsCode).groupContext).getObj()) {
//      log.info("local Channel_context:{}", channelContext);
//      TioUtils.checkBeforeIO(channelContext);
//    }

//    List<ChannelContext> channelContexts =
//      Tio.getAllChannelContexts(ShowcaseWsMsgHandler.CHANNEL_CONTEXT_MAP.get(wsCode).groupContext).getObj()
//        .stream().filter(item -> !ShowcaseWsMsgHandler.CHANNEL_CONTEXT_MAP.keySet().contains(item.toString())).collect(Collectors.toList());
//
//    for (ChannelContext channelContext : channelContexts) {
//      Tio.remove(channelContext, "remove unknow");
//    }

//    log.info("size:{}", Tio.getAllChannelContexts(ShowcaseWsMsgHandler.CHANNEL_CONTEXT_MAP.get(wsCode).groupContext).getObj().size());
//    ShowcaseWsMsgHandler.CHANNEL_CONTEXT_MAP.remove(wsCode);
  }

  @Transactional
  public void closeHandle(String wsCode) {
    handleHandshake.connetCloseHanle(wsCode);
  }
}
