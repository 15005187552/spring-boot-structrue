package com.ljwm.gecko.admin.security;

import com.ljwm.bootbase.security.IJwtAndSecurityAble;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.base.entity.Admin;
import com.ljwm.gecko.base.model.dto.AdminDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.io.PipedReader;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class JwtUser implements IJwtAndSecurityAble {

  private AdminDto admin;

  private Collection<? extends GrantedAuthority> grantedAuthorities;

  public JwtUser(AdminDto admin){
    this.admin = admin;
  }

  @Override
  public <T extends Serializable> T getId() {
    return (T) admin.getId();
  }

  @Override
  public Date getLastModifyPasswordTime() {
    return admin.getUpdateTime();
  }

  @Override
  public String getLoginType() {
    return LoginInfoHolder.getLoginType();
  }

  @Override
  public Map<String, String> extInfo() {
    return LoginInfoHolder.getExtInfo();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return admin.getPassword();
  }

  @Override
  public String getUsername() {
    return admin.getUsername();
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
    return true;
  }
}
