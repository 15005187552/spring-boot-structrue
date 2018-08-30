package com.ljwm.gecko.base.service;

import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.model.vo.WxResultMe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Janiffy
 * @date 2018/8/30 11:29
 */
@Service
public class MemberInfoService {

  @Autowired
  MemberInfoDao memberInfoDao;


  public WxResultMe findInfo(String userName) {
    return memberInfoDao.selectByUserName(userName);
  }
}
