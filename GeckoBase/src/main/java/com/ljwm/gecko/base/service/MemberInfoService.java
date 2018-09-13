package com.ljwm.gecko.base.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.dto.SqlFactory;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.mapper.CommonMapper;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberPaper;
import com.ljwm.gecko.base.entity.PaperPath;
import com.ljwm.gecko.base.enums.ValidateStatEnum;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.mapper.MemberPaperMapper;
import com.ljwm.gecko.base.mapper.MemberPasswordMapper;
import com.ljwm.gecko.base.mapper.PaperPathMapper;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.model.vo.LoginVo;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.base.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
  public void validateMember(MemberDto memberDto) {
    Member member = memberMapper.selectById(memberDto.getId());
    if (member == null) {
      log.info("根据会员id:{} 查询不到该会员信息", memberDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR, "根据会员id" + memberDto.getId() + "查询不到该会员信息!");
    }
    if (!Objects.equals(member.getValidateState(), ValidateStatEnum.INIT.getCode()) && !Objects.equals(member.getValidateState(), ValidateStatEnum.CONFIRM_FAILED.getCode())) {
      log.info("根据会员id:{} 认证中或认证通过,不可重复认证!", memberDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR, "该会员认证中或认证通过,不可重复认证!");
    }
    List<FileDto> fileDtoList = memberDto.getFileDtoList();
    if (CollectionUtils.isEmpty(fileDtoList)) {
      log.info("根据会员id:{}个人资质证件不能为空!", memberDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR, "个人资质证件不能为空!");
    }

    member.setMemberIdcard(memberDto.getMemberIdcard());
    member.setValidateState(ValidateStatEnum.WAIT_CONFIRM.getCode());
    File file = new File(appInfo.getFilePath() + Constant.MEMBER + member.getId());
    if (!file.exists()) {
      file.mkdirs();
    }
    if (StringUtils.isNotEmpty(memberDto.getPicFront())){
      String srcPath = appInfo.getFilePath() + Constant.CACHE + memberDto.getPicFront();
      String destDir = appInfo.getFilePath() + Constant.MEMBER + member.getId() + "/";
      Fileutil.cutGeneralFile(srcPath, destDir);
      member.setPicFront(Constant.MEMBER + member.getId() + "/" + memberDto.getPicFront());
    }
    if (StringUtils.isNotEmpty(memberDto.getPicBack())){
      String srcPath = appInfo.getFilePath() + Constant.CACHE + memberDto.getPicBack();
      String destDir = appInfo.getFilePath() + Constant.MEMBER + member.getId() + "/";
      Fileutil.cutGeneralFile(srcPath, destDir);
      member.setPicBack(Constant.MEMBER + member.getId() + "/" + memberDto.getPicBack());
    }
    if (StringUtils.isNotEmpty(memberDto.getPicPassport())){
      String srcPath = appInfo.getFilePath() + Constant.CACHE + memberDto.getPicPassport();
      String destDir = appInfo.getFilePath() + Constant.MEMBER + member.getId() + "/";
      Fileutil.cutGeneralFile(srcPath, destDir);
      member.setPicPassport(Constant.MEMBER + member.getId() + "/" + memberDto.getPicPassport());
    }
    memberMapper.updateById(member);
    for (FileDto fileDto : fileDtoList) {
      if (fileDto.getId()!=null){
       MemberPaper memberPaper = memberPaperMapper.selectById(fileDto.getId());
       if (memberPaper==null){
         log.info("会员{}资质认证详情查询不到该详情信息!",memberDto.getId());
         throw new LogicException(ResultEnum.DATA_ERROR,"查询不到该资质详情!");
       }
       if (Objects.equals(memberPaper.getValidateState(),ValidateStatEnum.CONFIRM_SUCCESS.getCode())){
         continue;
       }
       //如果为失败状态
       if (Objects.equals(memberPaper.getValidateState(),ValidateStatEnum.CONFIRM_FAILED.getCode())|| Objects.equals(memberPaper.getValidateState(),ValidateStatEnum.WAIT_CONFIRM.getCode())){
         paperPathMapper.delete(memberPaper.getId());
          for (String fileName : fileDto.getFileNameList()){
            PaperPath paperPath = new PaperPath();
            if (fileName.contains(Constant.MEMBER)){
              paperPath.setMemberPaperId(memberPaper.getId());
              paperPath.setPicPath(fileName);
            }else {
              String srcPath = appInfo.getFilePath() + Constant.CACHE + fileName;
              String destDir = appInfo.getFilePath() + Constant.MEMBER + member.getId() + "/";
              Fileutil.cutGeneralFile(srcPath, destDir);
              paperPath.setPicPath(Constant.MEMBER + member.getId() + "/" + fileName);
            }
            paperPath.setMemberPaperId(memberPaper.getId());
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
  }

  public MemberVo findMemberVoByRegMobile(String regMobile) {
    return memberMapper.findMemberVoByPhone(regMobile);
  }

  public Page<MemberVo> findByPage(MemberQueryDto memberQueryDto) {
    return commonService.find(memberQueryDto, (p, q) -> memberMapper.findByPage(p, Kv.by("text", memberQueryDto.getText()).set("disabled", memberQueryDto.getDisabled()).set("validateState", memberQueryDto.getValidateState())));
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
      if (Objects.equals(memberPaper.getValidateState(),ValidateStatEnum.CONFIRM_SUCCESS.getCode())){
        continue;
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
    member.setValidateState(validateState);
    memberMapper.updateById(member);
  }



  public LoginVo selectByPhone(String phoneNum) {
    List<LoginVo> list = memberPasswordMapper.selectByPhone(phoneNum);
    if(CollectionUtils.isNotEmpty(list)){
      return list.get(0);
    }
    return null;
  }
}
