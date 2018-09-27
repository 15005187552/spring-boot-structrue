package com.ljwm.gecko.im.ws;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ljwm.gecko.base.enums.SocketChannelEnum;
import com.ljwm.gecko.base.mapper.SocketInfoMapper;
import com.ljwm.gecko.im.service.HandleHandshake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
  private SocketInfoMapper socketInfoMapper;

  @Autowired
  private ShowcaseServerConfig showcaseServerConfig;

  @Autowired
  private HandleHandshake handleHandshake;

//  public static final Map<String, ChannelContext> CHANNEL_CONTEXT_MAP = new ConcurrentSkipListMap();

  /**
   * 握手时走这个方法，业务可以在这里获取cookie，request参数等
   */

  @Override
  public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
    String clientip = request.getClientIp();
    String id = request.getParam("id");

    Tio.bindUser(channelContext, id);

//    count++;
//    if(count == 2)
//    channelContexttstatic = channelContext;

    log.info("状态 1：{}");
    log.info("收到来自{}的ws握手包\r\n{}", clientip, request.toString());
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
  public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {

//    log.info(" Is Equel ：{}",channelContexttstatic == channelContext);
    //绑定到群组，后面会有群发
    Tio.bindGroup(channelContext, Const.GROUP_ID);


//    Tio.getAllChannelContexts()
    // todo
    String id = httpRequest.getParam("id");
    if (Objects.isNull(channelContext.getBsId())) {
      channelContext.setBsId(IdWorker.getIdStr());

      handleHandshake.connectHandle(id, httpRequest.getClientIp(), SocketChannelEnum.PROVIDER);

//      CHANNEL_CONTEXT_MAP.put(channelContext.getBsId(), channelContext);
    }

//    int count = Tio.getAllChannelContexts(channelContext.groupContext).getObj().size();
//
//    String msg = "{name:'admin',message:'" + channelContext.userid + " 进来了，共【" + count + "】人在线" + "'}";
//    //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
//    WsResponse wsResponse = WsResponse.fromText(msg, showcaseServerConfig.getCHARSET());
//    //群发
//    Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
  }

  /**
   * 字节消息（binaryType = arraybuffer）过来后会走这个方法
   */
  @Override
  public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
    return null;
  }

  /**
   * 当客户端发close flag时，会走这个方法
   */
  @Override
  public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
    Tio.remove(channelContext, "receive close flag");
    log.info("receive close flag");
//    handleHandshake.closeHandle(channelContext.toString());
    return null;
  }

  /*
   * 字符消息（binaryType = blob）过来后会走这个方法
   */
  @Override
  public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
    WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
    HttpRequest httpRequest = wsSessionContext.getHandshakeRequestPacket();//获取websocket握手包
    if (log.isDebugEnabled()) {
      log.debug("握手包:{}", httpRequest);
    }

    log.info("收到ws消息:{}", text);

    if (Objects.equals("心跳内容", text)) {
      return null;
    }
    //channelContext.getToken()
    //String msg = channelContext.getClientNode().toString() + " 说：" + text;
    String msg = "{name:'" + channelContext.userid + "',message:'" + text + "'}";
    //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
    WsResponse wsResponse = WsResponse.fromText(msg, showcaseServerConfig.getCHARSET());
    //群发
    Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);

    //返回值是要发送给客户端的内容，一般都是返回null
    return null;
  }

}
