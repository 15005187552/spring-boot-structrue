package com.ljwm.gecko.im.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
  private SessionDistributeService sessionDistributeService;

  @Autowired
  private CommonService commonService;

  @Autowired
  private SocketInfoMapper socketInfoMapper;

  @Autowired
  private PushMessageMapper pushMessageMapper;

  @Autowired
  private CustomerSessionMapper customerSessionMapper;

  @Transactional
  public void connectHandle(String code,String ip,SocketChannelEnum socketChannelEnum,String wsCode) {
//    String id = code.split("_")[0];
//    String loginType = code.split("_")[1];

    SocketInfo socketInfo = null;
    socketInfo = socketInfoMapper.selectOne(
      new QueryWrapper<SocketInfo>().eq("TARGET_ID",code)
//      .eq("CHANNEL",loginType)
    );


    if (socketInfo == null)
      socketInfo = new SocketInfo();
    socketInfo
      .setTargetTable(socketChannelEnum.getTableCode())
//      .setChannel(Integer.valueOf(loginType))
      .setConnectTime(DateUtil.date())
      .setTargetId(Long.valueOf(code))
      .setConnectContenxtId(wsCode)
      .setStatus(0) //todo 枚举状态 获取删除该字段
      .setIp(ip);

    // todo: 改成加入 推出的时候删除一个
    commonService.insertOrUpdate(socketInfo,socketInfoMapper);
//    socketInfoMapper.insert(socketInfo);

    log.info("customer online save:{}",code);
  }

  @Transactional
  public void connetCloseHanle(String code,String wsCode) {
//    List<SocketInfo> socketInfos = socketInfoMapper.selectByMap(Kv.by("TARGET_ID",id));
//    String id = code.split("_")[0];
//    String loginType = code.split("_")[1];
    QueryWrapper<SocketInfo> queryWrapper = new QueryWrapper<SocketInfo>()
      .eq("TARGET_ID",code)
//      .eq("CHANNEL",loginType)
      ;

    SocketInfo socketInfo = socketInfoMapper.selectOne(queryWrapper);

    if (socketInfo == null) throw new LogicException(ResultEnum.DATA_ERROR,"无法获取到socketInfo");
//    socketInfoMapper.deleteByMap(Kv.by("TARGET_ID",id));
    socketInfoMapper.delete(queryWrapper);
    log.info("id:{}连接登出",code);
  }

  /**
   * 信息分发
   *
   * @param message
   * @throws Exception
   */
  public void messageHandle(String message) throws Exception {
    String[] clazzAndMethod = JSONObject.parseObject(message).getString("method")
      .split(ReUtil.escape("#"));

    Object object = SpringKit.getBean(Class.forName(clazzAndMethod[0]));

    ReflectUtil.invoke(object,clazzAndMethod[1],message);
  }
}
