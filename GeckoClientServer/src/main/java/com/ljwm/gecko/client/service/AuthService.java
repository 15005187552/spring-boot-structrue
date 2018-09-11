package com.ljwm.gecko.client.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.JwtKit;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.UserSource;
import com.ljwm.gecko.base.model.vo.LoginVo;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.service.GuestService;
import com.ljwm.gecko.base.service.MemberInfoService;
import com.ljwm.gecko.base.service.WechatXCXService;
import com.ljwm.gecko.base.utils.FunctionUtil;
import com.ljwm.gecko.client.model.dto.GuestForm;
import com.ljwm.gecko.client.model.dto.LoginForm;
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
      log.info("Json from code: {}", jsonObject.toJSONString());
      log.info("befor {}", LoginInfoHolder.getExtInfo());
      // 存入TOKEN
      LoginInfoHolder.setExtInfo(
        Kv.by(SESSION_KEY, sessionKey)
          .set(MPOPENID, mpOpenId)
          .set(UNNIONID, unionId)
      );
      if(StrUtil.isNotBlank(guestForm.getRawData())&&StrUtil.isBlank(unionId)){
        String extInfo = wechatXCXService.getUserInfo(guestForm.getEncryptedData(), sessionKey, guestForm.getIv());
        log.debug("The ext info for wixin app user: {}", extInfo);
        JSONObject js = JSON.parseObject(extInfo);
        String nickName = js.getString("nickName");
        Long memberId = memberInfoService.updateExt(mpOpenId, extInfo, LoginType.WX_APP.getCode());
        String nName = memberInfoService.selectMember(memberId);
        if(StrUtil.isBlank(nName)){
          memberInfoService.updateMember(nickName, memberId);
        }
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

    MemberVo memberVo = memberInfoService.selectMemberInfo(guest.getMemberId(), LoginType.WX_APP.getCode());
    JwtUser jwtUser = new JwtUser(memberVo);
    ResultMe resultMe = new ResultMe();
    resultMe.setId(jwtUser.getId());
    resultMe.setIsGuest(false);
    resultMe.setAvatarPath(memberVo.getAvatarPath());
    resultMe.setPhoneNum(memberVo.getRegMobile());
    resultMe.setUsername(jwtUser.getUsername());
    resultMe.setExtInfo(memberVo.getAccount().getExtInfo());
    resultMe.setNickName(memberVo.getNickName());
    resultMe.setToken(JwtKit.generateToken(jwtUser));
    return resultMe;
  }

  public ResultMe me() {
    Long id = SecurityKit.currentId();
    //判断是否为会员
    String code = LoginInfoHolder.getLoginType();
    if (!LoginInfoHolder.getLoginType().equals(LoginType.GUEST.getCode().toString())){
        MemberVo memberVo = memberInfoService.selectMemberInfo(id, LoginInfoHolder.getLoginType());
        ResultMe resultMe = new ResultMe();
        resultMe.setIsGuest(false);
        resultMe.setId(id);
        resultMe.setAvatarPath(memberVo.getAvatarPath());
        resultMe.setPhoneNum(memberVo.getRegMobile());
        resultMe.setUsername(memberVo.getAccount().getUsername());
        resultMe.setExtInfo(memberVo.getAccount().getExtInfo());
        resultMe.setNickName(memberVo.getNickName());
        return resultMe;
    }
    return null;
  }

  public ResultMe loginSys(LoginForm loginForm) {
    String phoneNum = loginForm.getPhoneNum();
    String password = loginForm.getPassword();
    LoginVo loginVo = memberInfoService.selectByPhone(phoneNum);
    password = SecurityKit.passwordMD5(password, loginVo.getSalt());
    if(password.equals(loginVo.getPassword())){
      MemberVo memberVo = memberInfoService.selectMemberInfo(loginVo.getMemberId(), LoginType.MOBILE.getCode());
      JwtUser jwtUser = new JwtUser(memberVo);
      ResultMe resultMe = new ResultMe();
      resultMe.setId(jwtUser.getId());
      resultMe.setIsGuest(false);
      resultMe.setAvatarPath(memberVo.getAvatarPath());
      resultMe.setPhoneNum(memberVo.getRegMobile());
      resultMe.setUsername(jwtUser.getUsername());
      resultMe.setExtInfo(memberVo.getAccount().getExtInfo());
      resultMe.setNickName(memberVo.getNickName());
      resultMe.setToken(JwtKit.generateToken(jwtUser));
      return resultMe;
    }
    return null;
  }
}
