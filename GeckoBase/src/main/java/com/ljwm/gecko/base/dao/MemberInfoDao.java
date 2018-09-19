package com.ljwm.gecko.base.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberAccount;
import com.ljwm.gecko.base.entity.MemberPassword;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import com.ljwm.gecko.base.mapper.MemberAccountMapper;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.mapper.MemberPasswordMapper;
import com.ljwm.gecko.base.model.vo.MemberInfo;
import com.ljwm.gecko.base.model.vo.MemberVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/8/30 11:32
 */
@Repository
public class MemberInfoDao {

  @Autowired
  MemberMapper memberMapper;

  @Autowired
  MemberAccountMapper memberAccountMapper;

  @Autowired
  MemberPasswordMapper memberPasswordMapper;

  @Autowired
  CompanyUserMapper companyUserMapper;

  public MemberVo selectByUserName(String username) {
    return memberMapper.selectByUserName(username);
  }

  public Member insert(String phoneNum) {
    Member member = new Member();
    member.setRegMobile(phoneNum);
    member.setCreateTime(new Date());
    memberMapper.insert(member);
    return member;
  }

  public Long select(String phoneNum) {
    Map<String, Object> map = new HashedMap();
    map.put("REG_MOBILE", phoneNum);
    List<Member> list = memberMapper.selectByMap(map);
    if (CollectionUtil.isNotEmpty(list)) {
      return list.get(0).getId();
    }
    return null;
  }

  public MemberPassword insertPassword(String salt, String password, Date date) {
    MemberPassword memberPassword = new MemberPassword(null, password, salt, date);
    memberPasswordMapper.insert(memberPassword);
    return memberPassword;
  }

  public MemberAccount insertAccount(String userName, Integer code, Long memberId, Long passwordId) {
    MemberAccount memberAccount = new MemberAccount(null, userName, code, memberId, passwordId, null,null);
    memberAccountMapper.insert(memberAccount);
    return memberAccount;
  }

  public MemberVo selectMemberInfo(Long memberId, Integer code) {
    List<MemberVo> list = memberMapper.selectByMeVoAndCode(memberId, code);
    if(CollectionUtil.isNotEmpty(list)){
      return list.get(0);
    }
    return null;
  }

  public MemberInfo selectMemberInfo(Long memberId, String type) {
    List<MemberInfo> memberInfoList = memberMapper.selectAccountByType(memberId, type);
    if (CollectionUtil.isNotEmpty(memberInfoList)) {
      return memberInfoList.get(0);
    }
    return null;
  }
  public Long updateAccount(String mpOpenId, String extInfo, Integer code) {
    Map<String, Object> map = new HashedMap();
    map.put("TYPE", code);
    map.put("USERNAME", mpOpenId);
    List<MemberAccount> list = memberAccountMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      MemberAccount memberAccount = list.get(0);
      memberAccount.setExtInfo(extInfo);
      memberAccountMapper.updateById(memberAccount);
      return memberAccount.getMemberId();
    }
    return null;
  }

  public String selectMember(Long memberId) {
    Member member = memberMapper.selectById(memberId);
    if(member !=null){
      return member.getNickName();
    }
    return null;
  }

  public void updateMember(String nickName, Long memberId) {
    Member member = memberMapper.selectById(memberId);
    member.setNickName(nickName);
    memberMapper.updateById(member);
  }

  public List<MemberAccount> selectAccount(String phoneNum, Long memberId) {
    Map<String, Object> map = new HashedMap();
    map.put("USERNAME", phoneNum);
    map.put("MEMBER_ID", memberId);
    List<MemberAccount> list = memberAccountMapper.selectByMap(map);
    return list;
  }

  public List<MemberAccount> selectAccount(Long memberId) {
    Map<String, Object> map = new HashedMap();
    map.put("MEMBER_ID", memberId);
    List<MemberAccount> list = memberAccountMapper.selectByMap(map);
    return list;
  }

  public MemberInfo selectAccountByUserName(String username) {
    return memberMapper.selectAccountByUserName(username);
  }
}

