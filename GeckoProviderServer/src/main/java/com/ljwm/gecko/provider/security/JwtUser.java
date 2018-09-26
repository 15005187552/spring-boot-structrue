package com.ljwm.gecko.provider.security;

import com.ljwm.bootbase.security.IJwtAndSecurityAble;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.model.dto.AdminDto;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class JwtUser implements IJwtAndSecurityAble {

  private MemberVo member;

  private ProviderVo provider;

  public JwtUser(MemberVo member) {
    this.member = member;
  }

  @Override
  public <T extends Serializable> T getId() {
    return (T) member.getId();
  }

  @Override
  public Date getLastModifyPasswordTime() {
    return member.getAccount().getPassword().getLastModifyTime();
  }

  @Override
  public String getLoginType() {
    return member.getAccount().getType().toString();
  }

  @Override
  public Map<String, String> extInfo() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
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
