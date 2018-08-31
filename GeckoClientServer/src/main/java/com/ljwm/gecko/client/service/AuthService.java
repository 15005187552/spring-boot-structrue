package com.ljwm.gecko.client.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.JwtKit;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.UserSource;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.service.GuestService;
import com.ljwm.gecko.base.service.MemberInfoService;
import com.ljwm.gecko.base.service.WechatXCXService;
import com.ljwm.gecko.base.utils.FunctionUtil;
import com.ljwm.gecko.client.model.dto.GuestForm;
import com.ljwm.gecko.client.model.vo.ResultMe;
import com.ljwm.gecko.client.security.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by yuzhou on 2018/8/22.
 */
@Service
@Slf4j
public class AuthService {
  private final static String UNNIONID = "unionid";

  private final static String MPOPENID = "mp_openid";

  private final static String SESSION_KEY = "session_key";

  @Autowired
  private GuestService guestService;

  @Autowired
  private WechatXCXService wechatXCXService;

  @Autowired
  private MemberInfoService memberInfoService;

  @Transactional
  public ResultMe login(GuestForm guestForm) {

    UserSource userSource = UserSource.codeOf(guestForm.getSource());
    Guest guest = null;
    if(guestForm.getSource() == null ||guestForm.getSource() != UserSource.WX_APP.getCode()) {
      guest = guestService.upsert(userSource, guestForm.getGuestId(), null);
    } else {
      // 1. 验证CODE 并换取用户信息
      JSONObject jsonObject = new JSONObject();
      FunctionUtil.retryOnException(3, () -> wechatXCXService.doCodeLogin(guestForm.getCode(), jsonObject));
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
      if(StrUtil.isNotBlank(guestForm.getRawData())){
        String extInfo = wechatXCXService.getUserInfo(guestForm.getEncryptedData(), sessionKey, guestForm.getIv());
        memberInfoService.updateExt(mpOpenId, extInfo, UserSource.WX_APP.getCode());
      }
      guest = guestService.upsert(UserSource.codeOf(UserSource.WX_APP.getCode()), mpOpenId, null);
    }
    if(guest.getMemberId() == null) {
      JwtUser jwtUser = new JwtUser(guest);
      ResultMe resultMe = new ResultMe();
      resultMe.setId(jwtUser.getId());
      resultMe.setIsGuest(true);
      resultMe.setUsername(jwtUser.getUsername());
      resultMe.setToken(JwtKit.generateToken(jwtUser));
      return resultMe;
    }

    MemberVo memberVo = memberInfoService.selectMemberInfo(guest.getMemberId(), userSource.getCode());
    JwtUser jwtUser = new JwtUser(memberVo);
    ResultMe resultMe = new ResultMe();
    resultMe.setId(jwtUser.getId());
    resultMe.setIsGuest(false);
    resultMe.setUsername(jwtUser.getUsername());
    resultMe.setExtInfo(memberVo.getAccount().getExtInfo());
    resultMe.setNickName(memberVo.getNickName());
    resultMe.setToken(JwtKit.generateToken(jwtUser));
    return resultMe;
  }

  public Result me() {
    Long id = SecurityKit.currentId();
    //判断是否为会员
    if (!LoginInfoHolder.getLoginType().equals(LoginType.GUEST.getCode().toString())){
        return Result.success(memberInfoService.selectMemberInfo(id, LoginInfoHolder.getLoginType()));
    }
    return null;
  }
}
