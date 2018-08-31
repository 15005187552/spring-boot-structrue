package com.ljwm.gecko.base.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberAccount;
import com.ljwm.gecko.base.entity.MemberPassword;
import com.ljwm.gecko.base.mapper.MemberAccountMapper;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.mapper.MemberPasswordMapper;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.model.vo.WxResultMe;
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

  public  WxResultMe selectByUserName(String userName) {

    return  memberMapper.selectByUserName(userName);
  }

  public void insert(String phoneNum) {
    Member member = new Member();
    member.setRegMobile(phoneNum);
    memberMapper.insert(member);
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

  public void insertPassword(String salt, String password, Date date) {
    MemberPassword memberPassword = new MemberPassword(null, password, salt, date);
    memberPasswordMapper.insert(memberPassword);
  }

  public Long selectIdByPassword(String salt, String password){
    Map<String, Object> map = new HashedMap();
    map.put("SALT", salt);
    map.put("PASSWORD", password);
    List<Member> list = memberMapper.selectByMap(map);
    if (CollectionUtil.isNotEmpty(list)) {
      return list.get(0).getId();
    }
    return null;
  }

  public void insertAccount(String userName, Integer code, Long memberId, Long passwordId) {
    MemberAccount memberAccount = new MemberAccount(null, userName, code, memberId, passwordId, null);
    memberAccountMapper.insert(memberAccount);
  }

  public MemberVo selectMemberInfo(Long memberId, Integer code) {
    return memberMapper.selectByMeVo(memberId, code);
  }

  public void updateAccount(String mpOpenId, String extInfo, Integer code) {
    Map<String, Object> map = new HashedMap();
    map.put("TYPE", code);
    map.put("USERNAME", mpOpenId);
    List<MemberAccount> list = memberAccountMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      MemberAccount memberAccount = list.get(0);
      memberAccount.setExtInfo(extInfo);
      memberAccountMapper.updateById(memberAccount);
    }
  }
}

