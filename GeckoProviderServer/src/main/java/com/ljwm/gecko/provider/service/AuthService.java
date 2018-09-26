package com.ljwm.gecko.provider.service;

import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.JwtKit;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.base.enums.InfoValidateStateEnum;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.mapper.ProviderMapper;
import com.ljwm.gecko.base.model.vo.MemberInfo;
import com.ljwm.gecko.base.model.vo.ProviderSimpleVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.base.service.MemberInfoService;
import com.ljwm.gecko.provider.model.form.LoginForm;
import com.ljwm.gecko.provider.model.vo.ResultMe;
import com.ljwm.gecko.provider.security.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("all")
public class AuthService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private MemberInfoService memberInfoService;

  @Autowired
  private ProviderMapper providerMapper;

  private static final String loginType = "PROVIDER";


  public ResultMe login(LoginForm loginForm) {
    LoginInfoHolder.setLoginType(loginType);
    JwtUser jwtUser = validate(loginForm);
    return me(jwtUser).setToken(JwtKit.generateToken(jwtUser));
  }

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

  public ResultMe me(JwtUser jwtUser) {
    //判断是否为会员
    MemberInfo memberInfo = memberInfoService.selectMemberInfo(jwtUser.getId(), LoginType.MOBILE.getCode().toString());
    if (memberInfo==null){
      return null;
    }
    ResultMe resultMe = new ResultMe();
    resultMe.setId(jwtUser.getId());
    resultMe.setAvatarPath(memberInfo.getAvatarPath());
    resultMe.setPhoneNum(memberInfo.getRegMobile());
    resultMe.setUsername(memberInfo.getAccount().getUsername());
    resultMe.setExtInfo(memberInfo.getAccount().getExtInfo());
    resultMe.setNickName(memberInfo.getNickName());
    if (LoginInfoHolder.getLoginType().equals(loginType)){
      ProviderSimpleVo providerVo = providerMapper.findProviderSimpleVoByMemberId(jwtUser.getId());
      if (providerVo!=null && Objects.equals(providerVo.getInfoValidateState(),InfoValidateStateEnum.CONFIRM_SUCCESS.getCode())){
        resultMe.setIsProvider(true);
        resultMe.setProvider(providerVo);
      }else {
        resultMe.setIsProvider(false);
      }
      return resultMe;
    }
    return null;
  }

}
