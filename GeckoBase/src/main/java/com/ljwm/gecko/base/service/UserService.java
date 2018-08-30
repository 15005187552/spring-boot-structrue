package com.ljwm.gecko.base.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.UserSource;
import com.ljwm.gecko.base.model.dto.LoginWithSignature;
import com.ljwm.gecko.base.model.vo.WxResultMe;
import com.ljwm.gecko.base.utils.FunctionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Janiffy
 * @date 2018/8/30 9:08
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class UserService {

  private final static String UNNIONID = "unionid";

  private final static String MPOPENID = "mp_openid";

  private final static String SESSION_KEY = "session_key";

  @Autowired
  WechatXCXService wechatXCXService;

  @Autowired
  GuestService guestService;

  @Autowired
  MemberInfoService memberInfoService;

  @Transactional
  public WxResultMe mpCode(LoginWithSignature loginWithSignature) {
    // 1. 验证CODE 并换取用户信息
    JSONObject jsonObject = new JSONObject();
    FunctionUtil.retryOnException(3, () -> wechatXCXService.doCodeLogin(loginWithSignature.getCode(), jsonObject));
    if (StrUtil.isNotBlank(jsonObject.getString("errcode"))) // 认证出错
      throw new LogicException(ResultEnum.DATA_ERROR, jsonObject.getString("errcode"));
    String unionId = jsonObject.getString("unionid");
    String mpOpenId = jsonObject.getString("openid");
    String sessionKey = jsonObject.getString("session_key");
    log.info("befor {}", LoginInfoHolder.getExtInfo());
    // 存入TOKEN
    LoginInfoHolder.setExtInfo(
      Kv.by(SESSION_KEY, sessionKey)
        .set(MPOPENID, mpOpenId)
        .set(UNNIONID, unionId)
    );
    //该用户是否是游客
    Guest guest = guestService.find(mpOpenId);
    WxResultMe wxResultMe = new WxResultMe(null, mpOpenId, null);
    if (guest != null){
      //判断该用户是否为会员
      if(guest.getMemberId()!=null){
        return memberInfoService.findInfo(mpOpenId);
      }
    }
    guestService.upsert(UserSource.WX_APP, mpOpenId, null);
    return wxResultMe;
  }
}
