package com.ljwm.gecko.base.model.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Janiffy
 * @date 2018/8/29 18:07
 */
@Data
@Component
@Accessors(chain = true)
@ConfigurationProperties(prefix = "wx.xcx")
public class WechatConfig {

  private String appId;

  private String appSecret;

}
