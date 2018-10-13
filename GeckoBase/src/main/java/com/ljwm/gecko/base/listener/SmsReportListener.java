package com.ljwm.gecko.base.listener;

import cn.hutool.json.JSONObject;
import com.ljwm.aliyun.listener.ReceiveMessageListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuzhou on 2018/9/1.
 */
@Slf4j
public class SmsReportListener extends ReceiveMessageListener {

  @Override
  public void dealMessageContent(JSONObject content) throws Exception {
    log.info("Message sent: {}", content);
  }
}
