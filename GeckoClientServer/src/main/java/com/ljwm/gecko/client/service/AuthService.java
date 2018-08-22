package com.ljwm.gecko.client.service;

import com.ljwm.bootbase.security.JwtKit;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.UserSource;
import com.ljwm.gecko.base.service.GuestService;
import com.ljwm.gecko.client.model.dto.GuestForm;
import com.ljwm.gecko.client.model.vo.ResultMe;
import com.ljwm.gecko.client.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.Result;

/**
 * Created by yuzhou on 2018/8/22.
 */
@Service
public class AuthService {

  @Autowired
  private GuestService guestService;

  @Transactional
  public ResultMe loginAsGuest(GuestForm guestForm) {

    UserSource userSource = UserSource.codeOf(guestForm.getSource());
    Guest guest = guestService.upsert(userSource, guestForm.getGuestId(), null);

    JwtUser jwtUser = new JwtUser(guest);
    ResultMe resultMe = new ResultMe();
    resultMe.setId(jwtUser.getId());
    resultMe.setIsGuest(true);
    resultMe.setUsername(jwtUser.getUsername());
    resultMe.setToken(JwtKit.generateToken(jwtUser));

    return resultMe;
  }
}
