package com.ljwm.gecko.base.service;

import cn.hutool.core.util.RandomUtil;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.enums.UserSource;
import com.ljwm.gecko.base.mapper.GuestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 游客服务
 * Created by yuzhou on 2018/8/21.
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class GuestService {

  @Autowired
  private GuestMapper guestMapper;

  public Guest upsert(UserSource userSource, String guestId, String extInfo) {

    Guest guest = null;

    if (!StringUtils.isEmpty(guestId)) {
      guest = guestMapper.findByGuestId(guestId);
    }

    if (guest == null) {
      guest = new Guest();
      Date now = new Date();
      if(userSource.getCode() != UserSource.WX_APP.getCode()) {
        String uuid = RandomUtil.simpleUUID();
        guest.setGuestId(uuid)
          .setSourceType(userSource.getCode())
          .setExtInfo(extInfo)
          .setCreateTime(now)
          .setLastActiveTime(now);
      }else {
        guest.setGuestId(guestId)
          .setSourceType(userSource.getCode())
          .setExtInfo(extInfo)
          .setCreateTime(now)
          .setLastActiveTime(now);
      }
      guestMapper.insert(guest);
    } else {
      guest.setLastActiveTime(new Date());
      guestMapper.updateById(guest);
    }

    return guest;
  }

  public Guest find(String mpOpenId) {

    return guestMapper.findByGuestId(mpOpenId);
  }
}
