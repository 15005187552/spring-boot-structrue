package com.ljwm.gecko.provider.service;

import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.model.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProviderMemberService {

  @Autowired
  private MemberMapper memberMapper;

  public MemberVo findMemberById(Long memberId){
    return memberMapper.findMemberVoByMemberId(memberId);
  }
}
