package com.ljwm.gecko.im.runner;

import com.ljwm.gecko.im.ws.ShowcaseIpStatListener;
import com.ljwm.gecko.im.ws.ShowcaseServerAioListener;
import com.ljwm.gecko.im.ws.ShowcaseServerConfig;
import com.ljwm.gecko.im.ws.ShowcaseWsMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;

@Slf4j
@Component
public class TIoRunner implements ApplicationRunner {

  private WsServerStarter wsServerStarter;

  private ServerGroupContext serverGroupContext;

  @Autowired
  private ShowcaseServerAioListener showcaseServerAioListener;

  @Autowired
  private ShowcaseIpStatListener showcaseIpStatListener;

  @Autowired
  private ShowcaseWsMsgHandler showcaseWsMsgHandler;

  @Autowired
  private ShowcaseServerConfig showcaseServerConfig;


  /**
   * @author tanyaowu
   */
  public void showcaseWebsocketStarter(int port, ShowcaseWsMsgHandler wsMsgHandler) throws Exception {
    wsServerStarter = new WsServerStarter(port, wsMsgHandler);

    serverGroupContext = wsServerStarter.getServerGroupContext();
    serverGroupContext.setName(showcaseServerConfig.getPROTOCOL_NAME());
    serverGroupContext.setServerAioListener(showcaseServerAioListener);

    //设置ip监控
    serverGroupContext.setIpStatListener(showcaseIpStatListener);
    //设置ip统计时间段
    serverGroupContext.ipStats.addDurations(showcaseServerConfig.getIpStatDuration().getIPSTAT_DURATIONS());

    //设置心跳超时时间
    serverGroupContext.setHeartbeatTimeout(showcaseServerConfig.getHEARTBEAT_TIMEOUT());

    log.info("Tio Config : {}", showcaseServerConfig);

//    if (P.getInt("ws.use.ssl", 1) == 1) {
//      //如果你希望通过wss来访问，就加上下面的代码吧，不过首先你得有SSL证书（证书必须和域名相匹配，否则可能访问不了ssl）
//      String keyStoreFile = "classpath:config/ssl/keystore.jks";
//      String trustStoreFile = "classpath:config/ssl/keystore.jks";
//      String keyStorePwd = "214323428310224";
//      serverGroupContext.useSsl(keyStoreFile, trustStoreFile, keyStorePwd);
//    }
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {

    showcaseWebsocketStarter(showcaseServerConfig.getSERVER_PORT(), showcaseWsMsgHandler);

    wsServerStarter.start();

    log.info("Tio Started On Port : {}",showcaseServerConfig.getSERVER_PORT());
  }
}
