package com.ljwm.gecko.client.security;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ljwm.bootbase.security.LoginInfoHolder;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.entity.MemberPassword;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.mapper.GuestMapper;
import com.ljwm.gecko.base.model.vo.MemberInfo;
import com.ljwm.gecko.base.model.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.jam.mutable.MMember;
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

    LoginType loginType = LoginType.codeOf(LoginInfoHolder.getLoginType());

    log.debug("load user with username: {}, loginType: {} in userDetailsServiceImpl", username, loginType);

    switch (loginType) {
      case GUEST:
        Guest guest = guestMapper.findByGuestId(username);
        if (guest == null) {
          throw new UsernameNotFoundException("用户不存在");
        }
        return new JwtUser(guest, LoginInfoHolder.getExtInfo());

      case WX_APP:
        MemberInfo memberInfo = memberInfoDao.selectAccountByUserName(username);
        if(memberInfo == null) {
          throw new UsernameNotFoundException("用户不存在");
        }
        MemberVo memberVo = new MemberVo();
        BeanUtil.copyProperties(memberInfo, memberVo);
        memberVo.getAccount().setPassword(new MemberPassword().setLastModifyTime(DateUtil.offset(DateTime.now(), DateField.YEAR,-10)).setPassword(SecurityKit.passwordMD5(username,username)));
        LoginInfoHolder.setSalt(username);
        return new JwtUser(memberVo, LoginInfoHolder.getExtInfo());
      default:
        memberVo = memberInfoDao.selectByUserName(username);
        if(memberVo == null) {
          throw new UsernameNotFoundException("用户不存在");
        }
        LoginInfoHolder.setSalt(memberVo.getAccount().getPassword().getSalt());
        return new JwtUser(memberVo, LoginInfoHolder.getExtInfo());
    }
  }
}
