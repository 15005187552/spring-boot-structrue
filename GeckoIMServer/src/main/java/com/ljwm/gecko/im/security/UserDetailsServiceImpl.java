package com.ljwm.gecko.im.security;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.entity.MemberPassword;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.mapper.AdminMapper;
import com.ljwm.gecko.base.mapper.GuestMapper;
import com.ljwm.gecko.base.model.dto.AdminDto;
import com.ljwm.gecko.base.model.vo.MemberInfo;
import com.ljwm.gecko.base.model.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {


  @Autowired
  private GuestMapper guestMapper;

  @Autowired
  private MemberInfoDao memberInfoDao;

  @Autowired
  private AdminMapper adminMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    LoginType loginType = LoginType.codeOf(LoginInfoHolder.getLoginType());
    switch (loginType) {
      case GUEST:
        Guest guest = guestMapper.findByGuestId(username);
        if (guest == null) throw new UsernameNotFoundException("用户不存在");
        return new JwtUser(guest);

      case ADMIN:
        AdminDto admin = adminMapper.login(username);
        if (admin == null) throw new UsernameNotFoundException("用户不存在");
        LoginInfoHolder.setSalt(admin.getUsername());
        return new JwtUser(admin);

      case WX_APP:
        MemberInfo memberInfo = memberInfoDao.selectAccountByUserName(username);
        if (memberInfo == null) throw new UsernameNotFoundException("用户不存在");
        MemberVo memberVo = new MemberVo();
        BeanUtil.copyProperties(memberInfo,memberVo);
        memberVo.getAccount().setPassword(
          new MemberPassword().setLastModifyTime(DateUtil.offset(DateTime.now(),DateField.YEAR,-10))
            .setPassword(SecurityKit.passwordMD5(username,username))
        );
        LoginInfoHolder.setSalt(username);
        return new JwtUser(memberVo);

      default:
        memberVo = memberInfoDao.selectByUserName(username);
        if (memberVo == null) throw new UsernameNotFoundException("用户不存在");
        LoginInfoHolder.setSalt(memberVo.getAccount().getPassword().getSalt());
        return new JwtUser(memberVo);
    }
  }


}
