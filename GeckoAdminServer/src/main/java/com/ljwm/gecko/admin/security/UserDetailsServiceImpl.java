package com.ljwm.gecko.admin.security;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.base.mapper.AdminMapper;
import com.ljwm.gecko.base.model.bean.FunctionTree;
import com.ljwm.gecko.base.model.dto.AdminDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component("userDetailsServiceImpl")
@SuppressWarnings("all")
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final TimedCache<String, JwtUser> userPool = CacheUtil.newTimedCache(1000L * 60L * 10L);

  public static void removeCache() {
    userPool.clear();
  }

  private static final String loginType = "ADMIN";

  @Autowired
  private AdminMapper adminMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      JwtUser jwtUser = userPool.get(username);
      if (jwtUser == null) {
        AdminDto admin = adminMapper.login(username);
        LoginInfoHolder.setLoginType(loginType);
        jwtUser = new JwtUser(admin, getGrantedAuthorities(admin));
        userPool.put(username, jwtUser);
      }
      LoginInfoHolder.setSalt(jwtUser.getAdmin().getUsername());
      LoginInfoHolder.setExtInfo(jwtUser.extInfo());
      LoginInfoHolder.setLoginType(jwtUser.getLoginType());
      return jwtUser;
    } catch (Exception e) {
      log.info("用户登录失败:\n", e);
      throw new UsernameNotFoundException("用户名或密码错误!");
    }
  }

  private Collection<SimpleGrantedAuthority> getGrantedAuthorities(AdminDto admin) {
    return FunctionTree
      .createByRoles(admin.getRoles())
      .stream()
      .map(FunctionTree::getChildren)
      .map(functionTrees -> functionTrees.stream().map(FunctionTree::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
      .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
  }

}
