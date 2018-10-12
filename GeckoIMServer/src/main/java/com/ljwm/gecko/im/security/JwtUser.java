package com.ljwm.gecko.im.security;

import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.security.IJwtAndSecurityAble;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.model.dto.AdminDto;
import com.ljwm.gecko.base.model.vo.MemberVo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Author: xixil
 * Date: 2018/10/10 11:58
 * RUA
 */
@Data
@Accessors(chain = true)
public class JwtUser implements IJwtAndSecurityAble {

  public static final String ROLE_GUEST = "ROLE_GUEST";
  public static final String ROLE_MEMBER = "ROLE_MEMBER";
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  public static final String HAS_MEMBER_ROLE = "hasRole('ROLE_MEMBER')";

  public JwtUser(Guest guest) {
    this.guest = guest;
    this.member = null;
    this.admin = null;
  }

  public JwtUser(MemberVo member) {
    this.member = member;
    this.guest = null;
    this.admin = null;
  }

  public JwtUser(AdminDto admin) {
    this.admin = admin;
    this.guest = null;
    this.member = null;
  }

  private Guest guest;
  private AdminDto admin;
  private MemberVo member;

  private boolean isGuest() {
    return guest != null;
  }

  private boolean isAdmin() {
    return admin != null;
  }

  @Override
  public <T extends Serializable> T getId() {
    if (isGuest())
      return (T) guest.getId();
    if (isAdmin())
      return (T) admin.getId();
    return (T) member.getId();
  }

  @Override
  public Date getLastModifyPasswordTime() {
    if (isGuest())
      return guest.getCreateTime();
    if (isAdmin())
      return admin.getCreateTime();
    return member.getAccount().getPassword().getLastModifyTime();
  }

  @Override
  public String getLoginType() {
    if (isGuest())
      return LoginType.GUEST.getCode().toString();
    if (isAdmin())
      return LoginType.ADMIN.getCode().toString();
    return member.getAccount().getType().toString();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (isGuest())
      return Arrays.asList(new SimpleGrantedAuthority(ROLE_GUEST));
    if (isAdmin())
      return Arrays.asList(new SimpleGrantedAuthority(ROLE_ADMIN));
    return Arrays.asList(new SimpleGrantedAuthority(ROLE_MEMBER));
  }

  @Override
  public String getPassword() {
    if (isAdmin()) return admin.getPassword();
    return member.getAccount().getPassword().getPassword();
  }

  @Override
  public String getUsername() {
    if (isGuest()) return guest.getGuestId();
    if (isAdmin()) return admin.getUsername();
    return member.getAccount().getUsername();
  }

  @Override
  public Map<String, String> extInfo() {
    return Kv.create();
  }


  @Override
  public boolean isAccountNonExpired() {
    return isEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return isEnabled();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isEnabled();
  }

  @Override
  public boolean isEnabled() {
    return !member.getDisabled();
  }
}
