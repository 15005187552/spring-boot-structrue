package com.ljwm.gecko.im.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.kit.SpringKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.SocketInfo;
import com.ljwm.gecko.base.enums.SocketChannelEnum;
import com.ljwm.gecko.base.mapper.CustomerSessionMapper;
import com.ljwm.gecko.base.mapper.PushMessageMapper;
import com.ljwm.gecko.base.mapper.SocketInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@SuppressWarnings("all")
public class HandleTio {

  @Autowired
  private ChatMessageService chatMessageService;

  @Autowired
  private CommonService commonService;

  @Autowired
  private SocketInfoMapper socketInfoMapper;

  @Autowired
  private PushMessageMapper pushMessageMapper;

  @Autowired
  private CustomerSessionMapper customerSessionMapper;

  @Transactional
  public void connectHandle(String id, String ip, SocketChannelEnum socketChannelEnum, String wsCode) {
    // 处理异常socket
    SocketInfo socketInfo = new SocketInfo();
    socketInfo
      .setTargetTable(socketChannelEnum.getTableCode())
      .setChannel(socketChannelEnum.getCode())
      .setConnectTime(DateUtil.date())
      .setTargetId(Long.valueOf(id))
      .setConnectContenxtId(wsCode)
      .setStatus(0) //todo 枚举状态 获取删除该字段
      .setIp(ip);
    socketInfoMapper.insert(socketInfo);

    log.info("customer online save:{}", id);
  }

  @Transactional
  public void connetCloseHanle(String id, String wsCode) {
    List<SocketInfo> socketInfos = socketInfoMapper.selectByMap(Kv.by("TARGET_ID", id));
    if (socketInfos.size() <= 0) throw new LogicException(ResultEnum.DATA_ERROR, "无法获取到socketInfo");
    socketInfoMapper.deleteByMap(Kv.by("TARGET_ID", id));
    log.info("id:{}连接登出", id);
  }

  /**
   * 信息分发
   * @param message
   * @throws Exception
   */
  public void messageHandle(String message) throws Exception {

    String[] clazzAndMethod = JSONObject.parseObject(message).getString("method")
      .split(ReUtil.escape("#"));

    Object object = SpringKit.getBean(Class.forName(clazzAndMethod[0]));

    ReflectUtil.invoke(object, clazzAndMethod[1], message);
  }
}
