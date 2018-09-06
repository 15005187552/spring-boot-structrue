package com.ljwm.gecko.base.service;

import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.enums.ValidateStatEnum;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.FileDto;
import com.ljwm.gecko.base.model.dto.MemberDto;
import com.ljwm.gecko.base.model.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * @author Janiffy
 * @date 2018/8/30 11:29
 */
@Service
@Slf4j
public class MemberInfoService {

  @Autowired
  MemberInfoDao memberInfoDao;

  @Autowired
  private MemberMapper memberMapper;

  @Autowired
  private AppInfo appInfo;

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

  @Transactional
  public void validateMember(MemberDto memberDto){
    Member member = memberMapper.selectById(memberDto.getId());
    if (member==null){
      log.info("根据会员id:{} 查询不到该会员信息",memberDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"根据会员id"+memberDto.getId()+"查询不到该会员信息!" );
    }
    if (!Objects.equals(member.getValidateState(),ValidateStatEnum.INIT.getCode()) && !Objects.equals(member.getValidateState(),ValidateStatEnum.CONFIRM_FAILED.getCode())){
      log.info("根据会员id:{} 认证中或认证通过,不可重复认证!",memberDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"该会员认证中或认证通过,不可重复认证!");
    }
    List<FileDto> fileDtoList = memberDto.getFileDtoList();
    if (CollectionUtils.isEmpty(fileDtoList)){
      log.info("根据会员id:{}个人资质证件不能为空!",memberDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"个人资质证件不能为空!");
    }
    member.setMemberIdcard(memberDto.getMemberIdcard());
    memberMapper.updateById(member);
    File file = new File(appInfo.getFilePath()+ Constant.PERSON+ member.getId());
    if(!file.exists()){
      file.mkdirs();
    }
    for (FileDto fileDto: fileDtoList){

    }
  }
}
