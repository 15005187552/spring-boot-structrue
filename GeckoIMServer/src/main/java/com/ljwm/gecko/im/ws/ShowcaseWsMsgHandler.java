package com.ljwm.gecko.im.ws;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.base.enums.SocketChannelEnum;
import com.ljwm.gecko.base.mapper.SocketInfoMapper;
import com.ljwm.gecko.im.security.UserDetailsServiceImpl;
import com.ljwm.gecko.im.service.HandleTio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;


/**
 * @author tanyaowu
 * 2017年6月28日 下午5:32:38
 */

@Slf4j
@Component
@SuppressWarnings("all")
public class ShowcaseWsMsgHandler implements IWsMsgHandler {

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @Autowired
  private SocketInfoMapper socketInfoMapper;

  @Autowired
  private ShowcaseServerConfig showcaseServerConfig;

  @Autowired
  private HandleTio handleTio;

  public static final Map<String, GroupContext> GROUP_CONTEXT_MAP = new ConcurrentSkipListMap();

  public static GroupContext GROUP_CONTEXT = null;

  /**
   * 握手时走这个方法，业务可以在这里获取cookie，request参数等
   */

  @Override
  public HttpResponse handshake(HttpRequest request,HttpResponse httpResponse,ChannelContext channelContext) throws Exception {
    String id = request.getParam("id");
    Tio.bindUser(channelContext,id);

    if (!Boolean.valueOf(request.getParam("tiows_reconnect"))) {
      handleTio.connectHandle(id,request.getClientIp(),SocketChannelEnum.PROVIDER,channelContext.toString());
    }

    String clientip = request.getClientIp();
    log.info("收到来自{}的ws握手包\r\n{}",clientip,request.toString());
    return httpResponse;
  }

  /**
   * @param httpRequest
   * @param httpResponse
   * @param channelContext
   * @throws Exception
   * @author tanyaowu
   */
  @Override
  public void onAfterHandshaked(HttpRequest httpRequest,HttpResponse httpResponse,ChannelContext channelContext) throws Exception {

    log.info("ws onAfterHandshaked invoke");
    Tio.bindGroup(channelContext,Const.GROUP_ID);

    // todo
    String id = httpRequest.getParam("id");
  }


  /**
   * 当客户端发close flag时，会走这个方法
   */
  @Override
  public Object onClose(WsRequest wsRequest,byte[] bytes,ChannelContext channelContext) throws Exception {
    Tio.remove(channelContext,"receive close flag");

    log.info("receive close flag");
    handleTio.connetCloseHanle(channelContext.userid,channelContext.toString());
    return null;
  }

  /*
   * 字符消息（binaryType = blob）过来后会走这个方法
   */
  @Override
  public Object onText(WsRequest wsRequest,String text,ChannelContext channelContext) throws Exception {
//    WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
//    HttpRequest httpRequest = wsSessionContext.getHandshakeRequestPacket();//获取websocket握手包
//    if (log.isDebugEnabled()) {
//      log.debug("握手包:{}",httpRequest);
//    }
    String message = JSONUtil.toJsonStr(text);
    if (!JSONUtil.isJson(message))
      throw new LogicException("数据传输格式错误");

    if (StrUtil.equals(JSONObject.parseObject(message).getString("header"),"heartBeat")) {
        log.debug("心跳");
    }else{
      // 信息分发
      log.info("message start handle");
      handleTio.messageHandle(text);
    }

    return null;
  }

  /**
   * 字节消息（binaryType = arraybuffer）过来后会走这个方法
   */
  @Override
  public Object onBytes(WsRequest wsRequest,byte[] bytes,ChannelContext channelContext) throws Exception {
    return null;
  }

}
