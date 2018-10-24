package com.ljwm.gecko.provider.security;

import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.model.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {


  @Autowired
  private MemberMapper memberMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    MemberVo memberVo = memberMapper.selectByUserName(username);
    if (memberVo == null) {
      throw new UsernameNotFoundException("用户不存在");
    }
    LoginInfoHolder.setSalt(memberVo.getAccount().getPassword().getSalt());
    return new JwtUser(memberVo);
  }
}
