package com.ljwm.gecko.base.service;

import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.model.vo.MemberVo;
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

  public MemberVo selectMemberInfo(Long memberId, Integer code) {
    return memberInfoDao.selectMemberInfo(memberId, code);
  }

  public Long updateExt(String mpOpenId, String extInfo, Integer code) {
    return memberInfoDao.updateAccount(mpOpenId, extInfo, code);
  }

  public MemberVo selectMemberInfo(Long memberId, String type) {
    return memberInfoDao.selectMemberInfo(memberId, type);
  }

  public String selectMember(Long memberId) {
    return memberInfoDao.selectMember(memberId);
  }

  public void updateMember(String nickName, Long memberId) {
    memberInfoDao.updateMember(nickName, memberId);
  }
}
