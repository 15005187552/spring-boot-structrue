package com.ljwm.gecko.client.security;

import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.security.IJwtAndSecurityAble;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.model.vo.MemberVo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by yuzhou on 2018/8/22.
 */
@Getter
public class JwtUser implements IJwtAndSecurityAble {

  public static final String ROLE_GUEST = "ROLE_GUEST";
  public static final String ROLE_MEMBER = "ROLE_MEMBER";
  public static final String HAS_MEMEBER_ROLE = "hasRole('ROLE_MEMBER')";

  public JwtUser(Guest guest) {
    this.guest = guest;
    this.member = null;
  }

  public JwtUser(MemberVo member) {
    this.member = member;
    this.guest = null;
  }

  private Guest guest;
  private MemberVo member;

  private boolean isGuest() {
    return guest != null;
  }

  @Override
  public <T extends Serializable> T getId() {
    if (isGuest()) {
      return (T) guest.getId();
    }
    return (T) member.getId();
  }

  @Override
  public Date getLastModifyPasswordTime() {
    if (isGuest()) {
      return guest.getCreateTime();
    }
    return member.getAccount().getPassword().getLastModifyTime();
  }

  @Override
  public String getLoginType() {
    if (isGuest()) {
      return LoginType.GUEST.getCode().toString();
    }
    return member.getAccount().getType().toString();
  }

  @Override
  public Map<String, String> extInfo() {
    return Kv.create();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    if (isGuest()) {
      return Arrays.asList(new SimpleGrantedAuthority(ROLE_GUEST));
    }
    return Arrays.asList(new SimpleGrantedAuthority(ROLE_MEMBER));
  }

  @Override
  public String getPassword() {
    return member.getAccount().getPassword().getPassword();
  }

  @Override
  public String getUsername() {
    if (isGuest()) {
      return guest.getGuestId();
    }
    return member.getAccount().getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return !member.getDisabled();
  }
}
