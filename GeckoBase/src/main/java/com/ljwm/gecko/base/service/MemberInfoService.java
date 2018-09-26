package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.mapper.CommonMapper;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberPaper;
import com.ljwm.gecko.base.entity.PaperPath;
import com.ljwm.gecko.base.enums.InfoValidateStateEnum;
import com.ljwm.gecko.base.enums.ValidateStatEnum;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.mapper.MemberPaperMapper;
import com.ljwm.gecko.base.mapper.MemberPasswordMapper;
import com.ljwm.gecko.base.mapper.PaperPathMapper;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.model.vo.LoginVo;
import com.ljwm.gecko.base.model.vo.MemberInfo;
import com.ljwm.gecko.base.model.vo.MemberPaperVo;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.utils.Fileutil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@SuppressWarnings("all")
public class MemberInfoService {

  @Autowired
  MemberInfoDao memberInfoDao;

  @Autowired
  private MemberMapper memberMapper;

  @Autowired
  private MemberPaperMapper memberPaperMapper;

  @Autowired
  private MemberPasswordMapper memberPasswordMapper;

  @Autowired
  private AppInfo appInfo;

  @Autowired
  private CommonService commonService;

  @Autowired
  private CommonMapper commonMapper;

  @Autowired
  private PaperPathMapper paperPathMapper;

  @Autowired
  private MemberInfoService memberInfoService;


  public MemberVo selectMemberInfo(Long memberId, Integer code) {
    return memberInfoDao.selectMemberInfo(memberId, code);
  }

  @Transactional
  public Long updateExt(String mpOpenId, String extInfo, Integer code) {
    return memberInfoDao.updateAccount(mpOpenId, extInfo, code);
  }

  public MemberInfo selectMemberInfo(Long memberId, String type) {
    return memberInfoDao.selectMemberInfo(memberId, type);
  }

  public String selectMember(Long memberId) {
    return memberInfoDao.selectMember(memberId);
  }

  @Transactional
  public void updateMember(String nickName, Long memberId) {
    memberInfoDao.updateMember(nickName, memberId);
  }

  @Transactional
  public void validateMember(MemberDto memberDto) {
    Member member = memberMapper.selectById(memberDto.getId());
    if (member == null) {
      log.info("根据会员id:{} 查询不到该会员信息", memberDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR, "根据会员id" + memberDto.getId() + "查询不到该会员信息!");
    }
    List<FileDto> fileDtoList = memberDto.getFileDtoList();
    if (CollectionUtils.isEmpty(fileDtoList)) {
      log.info("根据会员id:{}个人资质证件不能为空!", memberDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR, "个人资质证件不能为空!");
    }
    member.setMemberIdcard(memberDto.getMemberIdcard());
    member.setName(memberDto.getName());
    File file = new File(appInfo.getFilePath() + Constant.MEMBER + member.getId());
    if (!file.exists()) {
      file.mkdirs();
    }
    if (StringUtils.isNotEmpty(memberDto.getPicFront())){
      if (memberDto.getPicFront().contains(Constant.MEMBER)){
        member.setPicFront(memberDto.getPicFront());
      }else {
        String srcPath = appInfo.getFilePath() + Constant.CACHE + memberDto.getPicFront();
        String destDir = appInfo.getFilePath() + Constant.MEMBER + member.getId() + "/";
        Fileutil.cutGeneralFile(srcPath, destDir);
        member.setPicFront(Constant.MEMBER + member.getId() + "/" + memberDto.getPicFront());
        member.setInfoValidateState(0);
      }
    }
    if (StringUtils.isNotEmpty(memberDto.getPicBack())){
      if (memberDto.getPicBack().contains(Constant.MEMBER)){
        member.setPicBack(memberDto.getPicBack());
      }else {
        String srcPath = appInfo.getFilePath() + Constant.CACHE + memberDto.getPicBack();
        String destDir = appInfo.getFilePath() + Constant.MEMBER + member.getId() + "/";
        Fileutil.cutGeneralFile(srcPath, destDir);
        member.setPicBack(Constant.MEMBER + member.getId() + "/" + memberDto.getPicBack());
        member.setInfoValidateState(0);
        member.setValidateText(StringUtils.EMPTY);
      }
    }
    if (StringUtils.isNotEmpty(memberDto.getPicPassport())){
      if (memberDto.getPicPassport().contains(Constant.MEMBER)){
        member.setPicPassport(memberDto.getPicBack());
      }else {
        String srcPath = appInfo.getFilePath() + Constant.CACHE + memberDto.getPicPassport();
        String destDir = appInfo.getFilePath() + Constant.MEMBER + member.getId() + "/";
        Fileutil.cutGeneralFile(srcPath, destDir);
        member.setPicPassport(Constant.MEMBER + member.getId() + "/" + memberDto.getPicPassport());
        member.setInfoValidateState(0);
        member.setValidateText(StringUtils.EMPTY);
      }
    }
    member.setVersion(member.getVersion()+1);
    Integer validateStatus = member.getValidateState();
    for (FileDto fileDto : fileDtoList) {
      if (fileDto.getId()!=null){
       MemberPaper memberPaper = memberPaperMapper.selectById(fileDto.getId());
       if (memberPaper==null){
         log.info("会员{}资质认证详情查询不到该详情信息!",memberDto.getId());
         throw new LogicException(ResultEnum.DATA_ERROR,"查询不到该资质详情!");
       }
       //如果没有修改
       if (Objects.equals(fileDto.getIsChange(),0)){
         MemberPaper tempPaper = new MemberPaper();
         BeanUtil.copyProperties(memberPaper,tempPaper);
         tempPaper.setVersion(member.getVersion());
         tempPaper.setId(null);
         memberPaperMapper.insert(tempPaper);
         for (String fileName : fileDto.getFileNameList()){
           PaperPath paperPath = new PaperPath();
           paperPath.setMemberPaperId(tempPaper.getId());
           paperPath.setPicPath(fileName);
           paperPath.setMemberPaperId(memberPaper.getId());
           paperPath.setCreateTime(DateUtil.date());
           paperPath.setUpdateTime(DateUtil.date());
           paperPathMapper.insert(paperPath);
         }
       }else {
          //修改了信息
         MemberPaper tempPaper = new MemberPaper();
         BeanUtil.copyProperties(memberPaper,tempPaper);
         tempPaper.setVersion(member.getVersion());
         tempPaper.setId(null);
         tempPaper.setValidateState(ValidateStatEnum.WAIT_CONFIRM.getCode());
         validateStatus = ValidateStatEnum.WAIT_CONFIRM.getCode();
         memberPaperMapper.insert(tempPaper);
         for (String fileName : fileDto.getFileNameList()){
           PaperPath paperPath = new PaperPath();
           if (fileName.contains(Constant.MEMBER)){
             paperPath.setMemberPaperId(tempPaper.getId());
             paperPath.setPicPath(fileName);
           }else {
             String srcPath = appInfo.getFilePath() + Constant.CACHE + fileName;
             String destDir = appInfo.getFilePath() + Constant.MEMBER + member.getId() + "/";
             Fileutil.cutGeneralFile(srcPath, destDir);
             paperPath.setPicPath(Constant.MEMBER + member.getId() + "/" + fileName);
           }
           paperPath.setMemberPaperId(tempPaper.getId());
           paperPath.setCreateTime(DateUtil.date());
           paperPath.setUpdateTime(DateUtil.date());
           paperPathMapper.insert(paperPath);
         }
       }
      }else {
        List<String> fileNameList = fileDto.getFileNameList();
        if (CollectionUtils.isEmpty(fileNameList)){
          log.info("会员{}资质认证,认证文件不能为空!",memberDto.getId());
          throw new LogicException(ResultEnum.DATA_ERROR,"认证文件不能为空!");
        }
        MemberPaper memberPaper = new MemberPaper();
        memberPaper.setPaperId(fileDto.getPaperId());
        memberPaper.setMemberId(memberDto.getId());
        memberPaper.setCreateTime(DateUtil.date());
        memberPaper.setUpdateTime(DateUtil.date());
        memberPaper.setValidateState(ValidateStatEnum.WAIT_CONFIRM.getCode());
        validateStatus = ValidateStatEnum.WAIT_CONFIRM.getCode();
        memberPaper.setVersion(member.getVersion());
        memberPaperMapper.insert(memberPaper);
        for (String fileName : fileNameList){
          String srcPath = appInfo.getFilePath() + Constant.CACHE + fileName;
          String destDir = appInfo.getFilePath() + Constant.MEMBER + member.getId() + "/";
          Fileutil.cutGeneralFile(srcPath, destDir);
          PaperPath paperPath = new PaperPath();
          paperPath.setMemberPaperId(memberPaper.getId());
          paperPath.setPicPath(Constant.MEMBER + member.getId() + "/" + fileName);
          paperPath.setCreateTime(DateUtil.date());
          paperPath.setUpdateTime(DateUtil.date());
          paperPathMapper.insert(paperPath);
        }
      }
    }
    member.setValidateState(ValidateStatEnum.WAIT_CONFIRM.getCode());
    memberMapper.updateById(member);
  }

  public MemberVo findMemberVoByRegMobile(String regMobile) {
    return memberMapper.findMemberVoByPhone(regMobile);
  }

  public Page<MemberVo> findByPage(MemberQueryDto memberQueryDto) {
    return commonService.find(memberQueryDto, (p, q) -> memberMapper.findByPage(p, Kv.by("text", memberQueryDto.getText()).set("disabled", memberQueryDto.getDisabled()).set("validateState", memberQueryDto.getValidateState()).set("infoValidateState",memberQueryDto.getInfoValidateState())));
  }

  @Transactional
  public void checkMember(MemberConfirmDto memberConfirmDto) {
    Member member = memberMapper.selectById(memberConfirmDto.getMemberId());
    if (member == null) {
      log.info("会员id:{} 会员信息不存在!", memberConfirmDto.getMemberId());
      throw new LogicException(ResultEnum.DATA_ERROR, "会员查询不到或非认证状态!");
    }
    List<MemberPaperConfirmDto> memberPaperConfirmDtoList = memberConfirmDto.getPaperConfirmDtoList();
    if (CollectionUtils.isEmpty(memberPaperConfirmDtoList)){
      log.info("会员{} ,资质认证详情不能为空!",memberConfirmDto.getMemberId());
      throw new LogicException(ResultEnum.DATA_ERROR,"资质认证详情不能为空!");
    }
    Integer validateState = ValidateStatEnum.CONFIRM_SUCCESS.getCode();
    for (MemberPaperConfirmDto memberPaperConfirmDto: memberPaperConfirmDtoList){
      MemberPaper memberPaper = memberPaperMapper.selectById(memberPaperConfirmDto.getId());
      if (memberPaper==null){
        throw new LogicException(ResultEnum.DATA_ERROR,"会员资质信息不存在!");
      }
      memberPaper.setUpdateTime(DateUtil.date());
      if (memberPaperConfirmDto.isAgree()) {
        memberPaper.setValidateState(ValidateStatEnum.CONFIRM_SUCCESS.getCode());
      } else {
        validateState = ValidateStatEnum.CONFIRM_FAILED.getCode();
        memberPaper.setValidateState(ValidateStatEnum.CONFIRM_FAILED.getCode());
      }
      memberPaper.setValidateText(memberPaperConfirmDto.getValidateText());
      memberPaper.setValidateTime(DateUtil.date());
      memberPaper.setValidatorId(memberConfirmDto.getValidatorId());
      memberPaperMapper.updateById(memberPaper);
    }
    List<MemberPaperVo> memberPaperVoList = memberPaperMapper.findMemberPaperVoListByMemberId(memberConfirmDto.getMemberId());
    Integer memberValidateStatus = ValidateStatEnum.CONFIRM_SUCCESS.getCode();
    for (MemberPaperVo memberPaperVo: memberPaperVoList){
      if (Objects.equals(memberPaperVo.getValidateState(),ValidateStatEnum.WAIT_CONFIRM.getCode())){
        memberValidateStatus = ValidateStatEnum.WAIT_CONFIRM.getCode();
        break;
      }
      if (Objects.equals(memberPaperVo.getValidateState(),ValidateStatEnum.CONFIRM_FAILED.getCode())){
        memberValidateStatus = ValidateStatEnum.CONFIRM_FAILED.getCode();
        break;
      }
    }
    member.setValidateState(memberValidateStatus);
    memberMapper.updateById(member);
  }

  @Transactional
  public void checkMemberInfo(MemberInfoConfirmDto memberInfoConfirmDto){
    Member member = memberMapper.selectById(memberInfoConfirmDto.getMemberId());
    if (member == null) {
      log.info("会员id:{} 会员信息不存在!", memberInfoConfirmDto.getMemberId());
      throw new LogicException(ResultEnum.DATA_ERROR, "会员查询不到");
    }
    if (!Objects.equals(member.getInfoValidateState(), InfoValidateStateEnum.INIT.getCode())){
      log.info("会员基本信息非待认证状态!");
      throw new LogicException(ResultEnum.DATA_ERROR,"会员基本信息非待认证状态!");
    }
    if (memberInfoConfirmDto.isAgree()){
      member.setInfoValidateState(InfoValidateStateEnum.CONFIRM_SUCCESS.getCode());
    }else {
      member.setInfoValidateState(InfoValidateStateEnum.CONFIRM_FAILED.getCode());
      member.setValidateText(memberInfoConfirmDto.getValidateText());
    }
    member.setValidatorId(memberInfoConfirmDto.getValidatorId());
    memberMapper.updateById(member);
  }

  public LoginVo selectByPhone(String phoneNum) {
    List<LoginVo> list = memberPasswordMapper.selectByPhone(phoneNum);
    if(CollectionUtils.isNotEmpty(list)){
      return list.get(0);
    }
    return null;
  }

  public MemberVo findMemberVoByMemberId(Long memberId){
    return memberMapper.findMemberVoByMemberId(memberId);
  }

  public Member findMemberByMobile(String regMobile) {
    return memberMapper.selectOne(new QueryWrapper<Member>().eq(Member.REG_MOBILE, regMobile));
  }
}
