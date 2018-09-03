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
import org.apache.tomcat.util.codec.binary.Base64;
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
  /*  // 被加密的数据
    byte[] dataByte = Base64.decode(encryptedData);
    // 加密秘钥
    byte[] keyByte = Base64.decode(sessionKey);
    // 偏移量
    byte[] ivByte = Base64.decode(iv);
    try {
      // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
      int base = 16;
      if (keyByte.length % base != 0)
      {
        int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
        byte[] temp = new byte[groups * base];
        Arrays.fill(temp, (byte) 0);
        System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
        keyByte = temp;
      }
      // 初始化
      Security.addProvider(new BouncyCastleProvider());
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
      SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
      AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
      parameters.init(new IvParameterSpec(ivByte));
      cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
      byte[] resultByte = cipher.doFinal(dataByte);
      if (null != resultByte && resultByte.length > 0)
      {
        String result = new String(resultByte, "UTF-8");
        return JSON.parseObject(result).toString();
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }*/
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
