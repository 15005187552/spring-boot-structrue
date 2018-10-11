package com.ljwm.gecko.im.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.Guest;
import com.ljwm.gecko.base.entity.PushMessage;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.mapper.PushMessageMapper;
import com.ljwm.gecko.base.model.dto.AdminDto;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.im.enums.CustomerEnum;
import com.ljwm.gecko.im.mapper.ImCustomerMessageMapper;
import com.ljwm.gecko.im.mapper.ImCustomerSessionMapper;
import com.ljwm.gecko.im.model.vo.MessagesVo;
import com.ljwm.gecko.im.model.vo.MineVo;
import com.ljwm.gecko.im.model.vo.PushMessageVo;
import com.ljwm.gecko.im.security.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Author: xixil
 * Date: 2018/9/30 13:44
 * RUA
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class CustomerService {

  @Autowired
  private PushMessageMapper pushMessageMapper;

  @Autowired
  private ImCustomerMessageMapper imCustomerMessageMapper;

  @Autowired
  private ImCustomerSessionMapper imCustomerSessionMapper;


  public MessagesVo findMessage() {
    JwtUser jwtUser = SecurityKit.currentUser();
    LoginType loginType = LoginType.codeOf(jwtUser.getLoginType());
    MessagesVo messagesVo = new MessagesVo();
    switch (loginType) {
      case ADMIN:
        return getAdmin(messagesVo,jwtUser.getAdmin());
      case GUEST:
        return getGuest(messagesVo,jwtUser.getGuest());
      default:
        return getMember(messagesVo,jwtUser.getMember());
    }
  }

  private MessagesVo getAdmin(MessagesVo messagesVo,AdminDto admin) {
    messagesVo.setMine(
      new MineVo().setId(admin.getId()).setUsername(admin.getNickName() == null ? admin.getUsername() : admin.getUsername())
    );

    messagesVo.setPushMessages(
      pushMessageMapper.selectList(new QueryWrapper<PushMessage>()
        .eq("TYPE",LoginType.ADMIN.getCode()).eq("RECEVIER_ID",admin.getId()))
        .stream().map(PushMessageVo::new).collect(Collectors.toList())
    );
    return messagesVo;
  }

  private MessagesVo getGuest(MessagesVo messagesVo,Guest guest) {
    messagesVo.setMine(new MineVo().setId(guest.getId()).setUsername(guest.getGuestId()));

    messagesVo.setPushMessages(
      pushMessageMapper.selectList(new QueryWrapper<PushMessage>()
        .eq("TYPE",LoginType.GUEST.getCode()).eq("RECEVIER_ID",guest.getId()))
        .stream().map(PushMessageVo::new).collect(Collectors.toList())
    );

    messagesVo.setSessions(imCustomerSessionMapper.getSessions(Kv.by("guestId",guest.getId())));
    return messagesVo;
  }

  private MessagesVo getMember(MessagesVo messagesVo,MemberVo member) {
    messagesVo.setMine(
      new MineVo().setId(member.getId()).setUsername(StrUtil.isNotBlank(member.getNickName()) ? member.getNickName() : member.getName())
    );

    messagesVo.setPushMessages(
      pushMessageMapper.selectList(new QueryWrapper<PushMessage>()
        .eq("RECEVIER_ID",member.getId())
        .and(wrapper -> wrapper.eq("TYPE",LoginType.WX_APP.getCode()))
        .or().eq("TYPE",LoginType.MOBILE.getCode())
      )
        .stream().map(PushMessageVo::new).collect(Collectors.toList())
    );

    messagesVo.setSessions(imCustomerSessionMapper.getSessions(Kv.by("memberId",member.getId())));
    return messagesVo;
  }
}
