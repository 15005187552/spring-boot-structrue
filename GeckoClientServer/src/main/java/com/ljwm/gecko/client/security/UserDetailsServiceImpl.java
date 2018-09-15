package com.ljwm.gecko.client.security;

import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.entity.MemberPassword;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.mapper.GuestMapper;
import com.ljwm.gecko.base.model.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 登录用户加载服务
 * Created by yuzhou on 2018/8/21.
 */
@Slf4j
@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private GuestMapper guestMapper;

  @Autowired
  private MemberInfoDao memberInfoDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("load user with username: {} in userDetailsServiceImpl", username);
    LoginType loginType = LoginType.codeOf(LoginInfoHolder.getLoginType());
    MemberVo memberVo = memberInfoDao.selectByUserName(username);
    switch (loginType) {
      case GUEST:
        Guest guest = guestMapper.findByGuestId(username);
        if (guest == null) {
          throw new UsernameNotFoundException("用户不存在");
        }
        return new JwtUser(guest);
      case WX_APP:
        if(memberVo == null) {
          throw new UsernameNotFoundException("用户不存在");
        }
        memberVo.getAccount().setPassword(new MemberPassword().setPassword(SecurityKit.passwordMD5(username,username)));
        LoginInfoHolder.setSalt(username);
        return new JwtUser(memberVo);
      default:
        if(memberVo == null) {
          throw new UsernameNotFoundException("用户不存在");
        }
        LoginInfoHolder.setSalt(memberVo.getAccount().getPassword().getSalt());
        return new JwtUser(memberVo);
    }
  }
}
