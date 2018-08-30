package com.ljwm.gecko.base.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.gecko.base.constant.Constant;
import com.ljwm.gecko.base.model.config.WechatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Janiffy
 * @date 2018/8/30 9:21
 */
@Service
@Slf4j
@SuppressWarnings({"unchecked"})
public class WechatXCXService {
  @Autowired
  private WechatConfig wechatConfig;

  /**
   * 微信小程序登錄
   *
   * @return
   */
  public Boolean doCodeLogin(String jsCode, JSONObject ret) {
    String retStr =
      HttpUtil.createGet(Constant.LOGIN_URL.replaceFirst("APPID", wechatConfig.getAppId()).replaceFirst("SECRET", wechatConfig.getAppSecret()).replaceFirst("JSCODE", jsCode))
        .execute()
        .body();
    JSONObject map = JSON.parseObject(retStr);
    ret.putAll(map);
    log.info("微信通讯返回: {}", ret);
    return true;
  }
}
