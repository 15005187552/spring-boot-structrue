package com.ljwm.gecko.client.bean;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JKhaled created by yunqisong@foxmail.com 2018-3-18
 * FOR : 系统微信公众号商户配置信息
 */
@Data
@Component
@Accessors(chain = true)
@ConfigurationProperties(prefix = "wx.mch")
public class WeiXinMch {

  private String mchId;

  private String paySecret;

  private String notifyUrl;

  private String notifyRemainUrl;
}
