package com.ljwm.gecko.client.security;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.security.IJwtAndSecurityAble;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.LoginType;
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
 * Created by yuzhou on 2018/8/22.
 */
@Data
@Accessors(chain = true)
public class JwtUser implements IJwtAndSecurityAble {

  public static final String ROLE_GUEST = "ROLE_GUEST";
  public static final String ROLE_MEMBER = "ROLE_MEMBER";
  public static final String HAS_MEMBER_ROLE = "hasRole('ROLE_MEMBER')";

  public JwtUser(Guest guest, Map exInfo) {
    this.guest = guest;
    this.member = null;
    this.exInfo = exInfo;
  }

  public JwtUser(MemberVo member, Map exInfo) {
    this.member = member;
    this.guest = null;
    this.exInfo = exInfo;
  }

  private Guest guest;
  private MemberVo member;
  private Map<String, String> exInfo;

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
      return DateUtil.offset(DateTime.now(), DateField.YEAR,-10);
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
    return  exInfo == null ? Kv.create() : exInfo;
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
