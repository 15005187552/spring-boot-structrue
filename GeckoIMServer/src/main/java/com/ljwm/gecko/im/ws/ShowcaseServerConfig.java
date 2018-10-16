/**
 *
 */
package com.ljwm.gecko.im.ws;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.tio.utils.time.Time;


/**
 * @author tanyaowu
 */
@Data
@Component
@Accessors(chain = true)
@ConfigurationProperties(prefix = "showcaseServerConfig")
public class ShowcaseServerConfig {

  @ApiModelProperty("监听端口")
  private int serverPort;

  private String CHARSET;

  @ApiModelProperty("监听的ip // null表示监听所有，并不指定ip")
  private String serverIp = null;

  @ApiModelProperty("心跳超时时间，单位：毫秒")
  private int heartBeatTimeOut;

  @ApiModelProperty("协议名字(可以随便取，主要用于开发人员辨识)")
  private String protocolName;

//  @ApiModelProperty("ip数据监控统计，时间段")
//  private IpStatDuration ipStatDuration = new IpStatDuration();
//
//  @Data
//  @Accessors(chain = true)
//  public static class IpStatDuration {
//    private Long DURATION_1 = Time.MINUTE_1 * 5;
//    private Long[] IPSTAT_DURATIONS = new Long[]{DURATION_1};
//  }

}


