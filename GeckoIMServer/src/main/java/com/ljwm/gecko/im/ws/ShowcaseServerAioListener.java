/**
 *
 */
package com.ljwm.gecko.im.ws;

import com.ljwm.gecko.im.service.HandleHandshake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.WsServerAioListener;

/**
 * @author tanyaowu
 * 用户根据情况来完成该类的实现
 */
@Component
@Slf4j
public class ShowcaseServerAioListener extends WsServerAioListener {


  @Autowired
  private ShowcaseServerConfig showcaseServerConfig;

  @Autowired
  private HandleHandshake handleHandshake;

  @Override
  public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
    super.onAfterConnected(channelContext, isConnected, isReconnect);
    if (log.isInfoEnabled()) {
      log.info("1  onAfterConnected\r\n{}", channelContext);
    }

    log.info("wo diao yong le ");
  }

  @Override
  public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {
    super.onAfterSent(channelContext, packet, isSentSuccess);
    if (log.isInfoEnabled()) {
      log.info("2  onAfterSent\r\n{}\r\n{}", packet.logstr(), channelContext);
    }
  }

  @Override
  public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
    super.onBeforeClose(channelContext, throwable, remark, isRemove);
    if (log.isInfoEnabled()) {
      log.info("3  onBeforeClose\r\n{}", channelContext);
    }
    // 关闭前的处理
//    handleHandshake.connetCloseHanle(channelContext.toString());
  }

  @Override
  public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {
    super.onAfterDecoded(channelContext, packet, packetSize);
    if (log.isInfoEnabled()) {
      log.info("4  onAfterDecoded\r\n{}\r\n{}", packet.logstr(), channelContext);
    }
  }

  @Override
  public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {
    super.onAfterReceivedBytes(channelContext, receivedBytes);
    if (log.isInfoEnabled()) {
      log.info("5   onAfterReceivedBytes\r\n{}", channelContext);
    }
  }

  @Override
  public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {
    super.onAfterHandled(channelContext, packet, cost);
    if (log.isInfoEnabled()) {
      log.info("6   onAfterHandled\r\n{}\r\n{}", packet.logstr(), channelContext.toString());
    }
    log.info("Aio handled");
  }

}
