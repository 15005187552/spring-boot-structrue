package com.ljwm.gecko.client.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.JwtKit;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.FormId;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.FormIdStatusEnum;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.UserSource;
import com.ljwm.gecko.base.mapper.FormIdMapper;
import com.ljwm.gecko.base.mapper.MemberAccountMapper;
import com.ljwm.gecko.base.model.vo.MemberInfo;
import com.ljwm.gecko.base.service.GuestService;
import com.ljwm.gecko.base.service.MemberInfoService;
import com.ljwm.gecko.base.service.WechatXCXService;
import com.ljwm.gecko.base.utils.FunctionUtil;
import com.ljwm.gecko.client.model.dto.FormIdForm;
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
import org.springframework.util.StringUtils;

import java.util.Map;
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
  private CommonService commonService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private FormIdMapper formIdMapper;

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


  /**
   * 小程序游客登录
   * @param guestForm
   * @return
   */
  @Transactional
  public ResultMe login(GuestForm guestForm) {

    String mpOpenId;
    String unionId;
    String sessionKey;

    Map extInfo = LoginInfoHolder.getExtInfo();
    log.info("Ext info of current user  {} and login type {}", extInfo, LoginInfoHolder.getLoginType());

    String code = guestForm.getCode();
    if (StringUtils.isEmpty(code)) { // code 为空时，从用户的token中获取信息
      // TODO: 没有传CODE的时间是否需要先判断 extInfo是否为空
      mpOpenId = (String)extInfo.get(MPOPENID);
      unionId = (String) extInfo.get(UNNIONID);
      sessionKey = (String)extInfo.get(SESSION_KEY);
    } else { // 如果前端传入CODE, 通过CODE到微信服务器换取用户信息
      JSONObject jsonObject = new JSONObject();
      FunctionUtil.retryOnException(3, () -> wechatXCXService.doCodeLogin(code, jsonObject));
      if (StrUtil.isNotBlank(jsonObject.getString("errcode"))) // 认证出错
        throw new LogicException(ResultEnum.DATA_ERROR, jsonObject.getString("errcode"));
      log.info("Json info from code: {}", jsonObject.toJSONString());
      unionId = jsonObject.getString("unionid");
      mpOpenId = jsonObject.getString("openid");
      sessionKey = jsonObject.getString("session_key");
      // 存入TOKEN
      LoginInfoHolder.setExtInfo(Kv.by(SESSION_KEY, sessionKey)
          .set(MPOPENID, mpOpenId)
          .set(UNNIONID, unionId));
    }

    if(mpOpenId == null) {
      throw new LogicException(ResultEnum.DATA_ERROR, "微信OpenID获取失败");
    }

    Guest guest = guestService.upsert(UserSource.codeOf(UserSource.WX_APP.getCode()), mpOpenId, null);
    if (guest.getMemberId() == null) { // 当前微信小程序用户仍然是游客
      LoginInfoHolder.setLoginType(LoginType.GUEST.getCode().toString());
      JwtUser jwtUser = new JwtUser(guest, LoginInfoHolder.getExtInfo());
      ResultMe resultMe = new ResultMe();
      resultMe.setId(jwtUser.getId());
      resultMe.setIsGuest(true);
      resultMe.setUsername(jwtUser.getUsername());
      resultMe.setToken(JwtKit.generateToken(jwtUser));
      return resultMe;
    } else { // 当前小程序用户已经是会员
      LoginInfoHolder.setLoginType(LoginType.WX_APP.getCode().toString());
      // 获取微信小程序用户的用户详情
      if (StrUtil.isNotBlank(guestForm.getRawData()) && StrUtil.isBlank(unionId)) {
        String userInfo = wechatXCXService.getUserInfo(guestForm.getEncryptedData(), sessionKey, guestForm.getIv());
        log.debug("The detail info for wixin app user: {}", userInfo);
        JSONObject js = JSON.parseObject(userInfo);
        String nickName = js.getString("nickName");
        String avatarUrl = js.getString("avatarUrl");

        Long memberId = memberInfoService.updateExt(mpOpenId, userInfo, LoginType.WX_APP.getCode());
        String nName = memberInfoService.selectMember(memberId);
        if (StrUtil.isBlank(nName)) {
          memberInfoService.updateMember(nickName, memberId, avatarUrl);
        }
      }

      // MemberVo memberVo = memberInfoService.selectMemberInfo(guest.getMemberId(), LoginType.WX_APP.getCode());
      // MemberAccount account = memberAccountMapper.selectOne(new QueryWrapper<MemberAccount>().eq(MemberAccount.USERNAME,mpOpenId));
      JwtUser jwtUser = validate(new LoginForm(mpOpenId, mpOpenId));
      return me(jwtUser).setToken(JwtKit.generateToken(jwtUser));
    }
  }

  public ResultMe me(JwtUser jwtUser) {
    //判断是否为会员
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

  public ResultMe uploadFormId(FormIdForm formIdForm) {
    if(!formIdForm.getFormId().equals("the formId is a mock one")) {
      FormId formId = new FormId()
        .setCreateTime(DateUtil.date())                               // 创建时间
        .setFormId(formIdForm.getFormId())                            // 表单ID
        .setMemberId(SecurityKit.currentId())                           // 用户ID
        .setStatus(FormIdStatusEnum.USE_ABLE.getCode())               // 状态
        ;
      commonService.insertOrUpdate(formId, formIdMapper);
    }
    return me(SecurityKit.currentUser());
  }
}
