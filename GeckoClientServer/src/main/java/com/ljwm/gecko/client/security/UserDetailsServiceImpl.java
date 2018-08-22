package com.ljwm.gecko.client.security;

import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.mapper.GuestMapper;
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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("load user with username: {} in userDetailsServiceImpl", username);
    LoginType loginType = LoginType.codeOf(LoginInfoHolder.getLoginType());
    switch (loginType) {
      case GUEST:
        Guest guest = guestMapper.findByGuestId(username);
        if (guest == null) {
          throw new UsernameNotFoundException("用户不存在");
        }
        return new JwtUser(guest);
      default:
        throw new UsernameNotFoundException("用户不存在");
    }
  }
}
