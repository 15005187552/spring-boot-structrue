package com.ljwm.gecko.client.webservice;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.base.utils.AesUtil;
import com.ljwm.gecko.client.bean.WeiXinGzh;
import com.ljwm.gecko.client.bean.WeiXinMch;
import com.ljwm.gecko.client.bean.WeiXinXcx;
import com.ljwm.gecko.client.model.dto.WxInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * JKhaled created by yunqisong@foxmail.com 2018-3-18
 * FOR : 微信小程序远程调用Service
 */
@Service
@Slf4j
@SuppressWarnings({"unchecked"})
public class WeiXinXcxService {
  // 登录url
  private static final String LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

  private static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

  @Autowired
  private WeiXinXcx weiXinXcx;

  @Autowired
  private WeiXinGzh weiXinGzh;

  @Autowired
  private WeiXinMch weiXinMch;

  /**
   * 获取AccessToken
   *
   * @return
   */
  private String getAccessToken() {
    return HttpUtils.get(ACCESS_TOKEN.replaceFirst("APPID", weiXinXcx.getAppId()).replaceFirst("APPSECRET", weiXinXcx.getAppSecret()));
  }

  /**
   * 微信小程序登錄
   *
   * @return
   */
  public Boolean doCodeLogin(String jsCode, JSONObject ret) {
    String retStr =
      HttpUtil.createGet(LOGIN_URL.replaceFirst("APPID", weiXinXcx.getAppId()).replaceFirst("SECRET", weiXinXcx.getAppSecret()).replaceFirst("JSCODE", jsCode))
        .execute()
        .body();
    JSONObject map = JSON.parseObject(retStr);
    ret.putAll(map);
    log.info("微信通讯返回: {}", ret);
    return true;
  }

  /**
   * 解密微信签名数据
   *
   * @param encryptedData
   * @param sessionKey
   * @param iv
   * @return
   */
  public WxInfo getWeiXinInfo(String encryptedData, String sessionKey, String iv) {
      String userInfo = decrypt(encryptedData,sessionKey,iv);
      return JSON.parseObject(userInfo, WxInfo.class);
  }
  public String decrypt(String encryptedData, String sessionKey, String iv) {
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

  /**
   * 统一下单接口
   *
   * @param ip     下单IP
   * @param num    商户订单号
   * @param amount 金额 单位 分
   * @param body   订单body
   * @param xcx    是否是小程序
   * @return
   */
  public Map weixinPay(String ip, String num, String amount, String openId, String body, Boolean xcx,Boolean down) {
    // 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
    // 调起微信支付，统一下单
    Map<String, String> params = new HashMap<String, String>();

    if (!xcx)
      params.put("appid", weiXinGzh.getAppId());
    else
      params.put("appid", weiXinXcx.getAppId());

    params.put("openid", openId);
    params.put("trade_type", PaymentApi.TradeType.JSAPI.name()); //交易类型app：手机app支付，NATIVE：返回支付连接，可转成二维码客户扫描支付
    params.put("mch_id", weiXinMch.getMchId());
    params.put("device_info", "WEB");
    params.put("body", body);
    params.put("out_trade_no", num); //订单编号
    params.put("total_fee", amount); //订单金额  单位为分
    params.put("spbill_create_ip", ip);  //终端ip
    params.put("nonce_str", String.valueOf(System.currentTimeMillis()));
    params.put("notify_url", down?weiXinMch.getNotifyRemainUrl():weiXinMch.getNotifyUrl()); //支付后通知回调地址
    String sign = PaymentKit.createSign(params, weiXinMch.getPaySecret());    //生成签名
    params.put("sign", sign);

    log.debug("The params for create wei xin payment: {}", params);

    String xmlResult = PaymentApi.pushOrder(params);
    Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

    log.debug("The result of create wei xin payment: {}", result);

    log.info("result: {}", result);
    String return_code = result.get("return_code");

    if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
      return new Kv().set("return_msg", "FALSE"); //  错误
    }

    String result_code = result.get("result_code");
    if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
      return new Kv().set("result_code", "FALSE");  //错误
    }
    Map ret = PaymentKit.xmlToMap(xmlResult);
    return reSign(ret);
  }


  /**
   * 根据返回数据重新计算签名
   *
   * @param map
   * @return
   */
  public Map reSign(Map map) {
    Kv para = Kv.create();
    Kv data = Kv.create();
    String prepay_id = (String) map.get("prepay_id");
    String nonce_str = (String) map.get("nonce_str");

    para.put("appId", weiXinXcx.getAppId());
    para.put("timeStamp", String.valueOf(System.currentTimeMillis()));
    para.put("nonceStr", nonce_str);
    para.put("package", "prepay_id=" + prepay_id);
    para.put("signType", "MD5");

    data.put("appId", weiXinXcx.getAppId());
    data.put("package", "prepay_id=" + prepay_id);
    data.put("timeStamp", String.valueOf(System.currentTimeMillis()));
    data.put("nonceStr", nonce_str);
    data.put("signType", "MD5");
    data.put("sign", PaymentKit.createSign(para, weiXinMch.getPaySecret()));

    return data;
  }

  /**
   * 错误码
   *
   * @param code
   * @return
   */
  public String getErrorMsg(String code) {
    switch (code) {
      case "NO_AUTH":
        return "没有该接口权限";
      case "AMOUNT_LIMIT":
        return "付款金额不能小于最低限额";
      case "PARAM_ERROR":
        return "参数错误";
      case "OPENID_ERROR":
        return "Openid错误";
      case "SEND_FAILED":
        return "付款错误";
      case "NOTENOUGH":
        return "余额不足";
      case "SYSTEMERROR":
        return "系统繁忙，请稍后再试";
      case "NAME_MISMATCH":
        return "姓名校验出错";
      case "SIGN_ERROR":
        return "签名错误";
      case "XML_ERROR":
        return "Post内容出错";
      case "FATAL_ERROR":
        return "两次请求参数不一致";
      case "FREQ_LIMIT":
        return "超过频率限制，请稍后再试。";
      case "MONEY_LIMIT":
        return "已经达到今日付款总额上限/已达到付款给此用户额度上限";
      case "CA_ERROR":
        return "证书出错";
      case "V2_ACCOUNT_SIMPLE_BAN":
        return "无法给非实名用户付款";
      case "PARAM_IS_NOT_UTF8":
        return "请求参数中包含非utf8编码字符";
    }
    return null;
  }

  /**
   * 查询付款状态
   *
   * @param wxNum
   * @return
   */
  public Map weixinPayQuery(String wxNum) {

    return PaymentApi.queryByOutTradeNo(weiXinXcx.getAppId(), weiXinMch.getMchId(), weiXinMch.getPaySecret(), wxNum);
  }
}
