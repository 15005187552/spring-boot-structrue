/**
 *
 */
package com.ljwm.gecko.im.ws;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import org.tio.utils.time.Time;

/**
 * @author tanyaowu
 */
@Component
@Data
@Accessors(chain = true)
public class ShowcaseServerConfig {
  /**
   * 协议名字(可以随便取，主要用于开发人员辨识)
   */
  private String PROTOCOL_NAME = "showcase";

  private String CHARSET = "utf-8";
  /**
   * 监听的ip
   */
  private String SERVER_IP = null;//null表示监听所有，并不指定ip

  /**
   * 监听端口
   */
  private int SERVER_PORT = 9300;

  /**
   * 心跳超时时间，单位：毫秒
   */
  private int HEARTBEAT_TIMEOUT = 1000 * 60;

  private IpStatDuration ipStatDuration = new IpStatDuration();

  /**
   * ip数据监控统计，时间段
   *
   * @author tanyaowu
   */
  @Data
  @Accessors(chain = true)
  public static class IpStatDuration {
    private Long DURATION_1 = Time.MINUTE_1 * 5;
    private Long[] IPSTAT_DURATIONS = new Long[]{DURATION_1};
  }

}
