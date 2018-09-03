package com.ljwm.gecko.base.service;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.base.constant.Constant;
import com.ljwm.gecko.base.model.config.WechatConfig;
import com.ljwm.gecko.base.utils.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
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
   * 微信小程序登录
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

  public String getUserInfo(String encryptedData, String sessionKey, String iv) {
    try {
      AesUtil aes = new AesUtil();
      byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
      if (null != resultByte && resultByte.length > 0) {
        String userInfo = new String(resultByte, "UTF-8");
        return userInfo;
      }
    } catch (Exception e) {
      throw new LogicException(ResultEnum.DATA_ERROR, "解密微信签名数据失败!");
    }
    return null;
  }
}
