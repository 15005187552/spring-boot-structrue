package com.ljwm.gecko.client.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.JwtKit;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.UserSource;
import com.ljwm.gecko.base.mapper.MemberAccountMapper;
import com.ljwm.gecko.base.model.vo.MemberInfo;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


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

  @Autowired
  private MemberAccountMapper memberAccountMapper;

  @Autowired
  private AuthenticationManager authenticationManager;

  /**
   * 公共验证逻辑实现
   *
   * @param loginForm
   * @return
   */
  private JwtUser validate(LoginForm loginForm) {
    return Optional
      .ofNullable(loginForm)
      .map(form -> new UsernamePasswordAuthenticationToken(form.getPhoneNum(), form.getPassword()))
      .map(upToken -> authenticationManager.authenticate(upToken))
      .map(authentication -> {
        if (!authentication.isAuthenticated())
          throw new LogicException(ResultEnum.BAD_CREDENTIALS);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("登陆成功, 为该用户创建Token: {} ", loginForm);
        return (JwtUser) authentication.getPrincipal();
      })
      .get();
  }


  @Transactional
  public ResultMe login(GuestForm guestForm) {
    Guest guest = null;
    String mpOpenId = null;
      // 1. 验证CODE 并换取用户信息
      JSONObject jsonObject = new JSONObject();
      FunctionUtil.retryOnException(3, () -> wechatXCXService.doCodeLogin(guestForm.getCode(), jsonObject));
      if (StrUtil.isNotBlank(jsonObject.getString("errcode"))) // 认证出错
        throw new LogicException(ResultEnum.DATA_ERROR, jsonObject.getString("errcode"));
      String unionId = jsonObject.getString("unionid");
      mpOpenId = jsonObject.getString("openid");
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
    if(guest.getMemberId() == null) {
      LoginInfoHolder.setLoginType(LoginType.GUEST.getCode().toString());
      JwtUser jwtUser = new JwtUser(guest);
      ResultMe resultMe = new ResultMe();
      resultMe.setId(jwtUser.getId());
      resultMe.setIsGuest(true);
      resultMe.setUsername(jwtUser.getUsername());
      resultMe.setToken(JwtKit.generateToken(jwtUser));
      return resultMe;
    }
    LoginInfoHolder.setLoginType(LoginType.WX_APP.getCode().toString());
//    MemberVo memberVo = memberInfoService.selectMemberInfo(guest.getMemberId(), LoginType.WX_APP.getCode());
   // MemberAccount account = memberAccountMapper.selectOne(new QueryWrapper<MemberAccount>().eq(MemberAccount.USERNAME,mpOpenId));
    JwtUser jwtUser = validate(new LoginForm(mpOpenId,mpOpenId));

    return  me(jwtUser).setToken(JwtKit.generateToken(jwtUser));
  }

  public ResultMe me(JwtUser jwtUser) {
    //判断是否为会员
    String code = LoginInfoHolder.getLoginType();
    if (!LoginInfoHolder.getLoginType().equals(LoginType.GUEST.getCode().toString())){
        MemberInfo memberInfo = memberInfoService.selectMemberInfo(jwtUser.getId(), LoginInfoHolder.getLoginType());
        ResultMe resultMe = new ResultMe();
        resultMe.setIsGuest(false);
        resultMe.setId(jwtUser.getId());
        resultMe.setAvatarPath(memberInfo.getAvatarPath());
        resultMe.setPhoneNum(memberInfo.getRegMobile());
        resultMe.setUsername(memberInfo.getAccount().getUsername());
        resultMe.setExtInfo(memberInfo.getAccount().getExtInfo());
        resultMe.setNickName(memberInfo.getNickName());
        return resultMe;
    }
    return null;
  }

  public ResultMe loginSys(LoginForm loginForm) {
    LoginInfoHolder.setLoginType(LoginType.MOBILE.getCode().toString());
    JwtUser jwtUser = validate(loginForm);
    return me(jwtUser).setToken(JwtKit.generateToken(jwtUser));
   /* String phoneNum = loginForm.getPhoneNum();
    String password = loginForm.getPassword();
    LoginVo loginVo = memberInfoService.selectByPhone(phoneNum);
    if(loginVo == null){
      return Result.fail("密码错误!");
    }
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
      return Result.success(resultMe);
    }
    return Result.fail("密码错误!");*/
  }
}
