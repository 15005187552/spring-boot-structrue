package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.dto.SqlFactory;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.mapper.CommonMapper;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.ProviderStatEnum;
import com.ljwm.gecko.base.enums.ProviderTypeEnum;
import com.ljwm.gecko.base.enums.ValidateStatEnum;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.model.vo.MemberPaperVo;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.model.vo.ProviderServicesVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.base.model.vo.admin.ServiceTypeTree;
import com.ljwm.gecko.base.utils.Fileutil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class ProviderService {

  @Autowired
  private ProviderMapper providerMapper;

  @Autowired
  private MemberMapper memberMapper;

  @Autowired
  private CommonMapper commonMapper;

  @Autowired
  private MemberPaperMapper memberPaperMapper;

  @Autowired
  private ProviderServicesMapper providerServicesMapper;

  @Autowired
  private ProviderUserMapper providerUserMapper;

  @Autowired
  private AppInfo appInfo;

  @Autowired
  private ProviderPaperMapper providerPaperMapper;

  @Autowired
  private CommonService commonService;

  @Transactional
  public void saveProvider(ProviderDto providerDto) {
    //申请个人
    if (Objects.equals(providerDto.getType(), ProviderTypeEnum.PERSON.getCode())){
      Member member = memberMapper.selectById(providerDto.getMemberId());
      if (member==null){
        log.info("会员{},查询不到该会员的信息!",providerDto.getMemberId());
        throw new LogicException(ResultEnum.DATA_ERROR,"查询不到该会员信息!");
      }
      List<MemberPaperVo> memberPaperVoList = memberPaperMapper.findCheckedMemberPaperVoListByMemberId(providerDto.getMemberId());
      if (CollectionUtils.isEmpty(memberPaperVoList)){
        log.info("认证人没有认证,不能提交个人服务商入驻!");
        throw new LogicException(ResultEnum.DATA_ERROR,"认证人没有认证,不能提交个人服务商入驻!");
      }
      List<ProviderServiceDto> providerServiceDtoList = providerDto.getProviderServiceDtoList();
      if (CollectionUtils.isEmpty(providerServiceDtoList)){
        log.info("会员{}个人服务商入驻,服务类型不能为空",providerDto.getMemberId());
        throw new LogicException(ResultEnum.DATA_ERROR,"个人服务商入驻,服务类型不能为空");
      }
      if (providerDto.getId()!= null){
        //服务商不为空,修改服务商
        Provider provider = providerMapper.selectById(providerDto.getId());
        if (provider==null){
          log.info("个人服务商入驻,服务商id {},查询不到该服务商信息!",providerDto.getId());
          throw new LogicException(ResultEnum.DATA_ERROR,"查询不到该服务商信息!");
        }
        provider.setVersion(provider.getVersion()+1);
        for (ProviderServiceDto providerServiceDto: providerServiceDtoList){
          ProviderServices providerServices = new ProviderServices();
          if (providerServiceDto.getId()!=null){
            providerServices = providerServicesMapper.selectById(providerServiceDto.getId());
            BeanUtil.copyProperties(providerDto,providerServices);
            providerServices.setVersion(provider.getVersion());
            providerServices.setId(null);
            if (!Objects.equals(providerServices.getValidateState(),ProviderStatEnum.CONFIRM_SUCCESS.getCode())){
              providerServices.setValidateState(ProviderStatEnum.WAIT_CONFIRM.getCode());
            }
          }else {
            BeanUtil.copyProperties(providerServiceDto,providerServices);
            providerServices.setValidateState(ProviderStatEnum.WAIT_CONFIRM.getCode());
            providerServices.setVersion(provider.getVersion());
            providerServices.setUpdateTime(DateUtil.date());
            providerServices.setCreateTime(DateUtil.date());
          }
          providerServicesMapper.insert(providerServices);
        }
      }else {
        //服务商为空,新增服务商
        Provider provider = new Provider();
        BeanUtil.copyProperties(providerDto,provider);
        provider.setVersion(1);
        provider.setValidateState(ProviderStatEnum.WAIT_CONFIRM.getCode());
        providerMapper.insert(provider);
        for (ProviderServiceDto providerServiceDto: providerServiceDtoList){
          ProviderServices providerServices = new ProviderServices();
          providerServices.setProviderId(provider.getId());
          providerServices.setServiceId(providerServiceDto.getServiceId());
          providerServices.setValidateState(ProviderStatEnum.WAIT_CONFIRM.getCode());
          providerServices.setCreateTime(DateUtil.date());
          providerServices.setUpdateTime(DateUtil.date());
          providerServices.setVersion(provider.getVersion());
          providerServicesMapper.insert(providerServices);
        }
      }
    }else {
      //申请企业
      Member member = memberMapper.selectById(providerDto.getMemberId());
      if (member == null) {
        log.info("会员id{},查询不到此会员信息!");
        throw new LogicException(ResultEnum.DATA_ERROR, "查询不到此会员信息");
      }
      List<Long> memberIds = Lists.newArrayList();
      List<MemberPaperVo> memberPaperVoList = memberPaperMapper.findCheckedMemberPaperVoListByMemberId(providerDto.getMemberId());
      if (CollectionUtils.isEmpty(memberPaperVoList)) {
        if (CollectionUtils.isEmpty(providerDto.getMemberIds())) {
          log.info("入驻人没有认证,认证人不能为空!");
          throw new LogicException(ResultEnum.DATA_ERROR, "认证人不能为空!");
        }
      }else {
        memberIds.add(providerDto.getMemberId());
      }
      if (CollectionUtils.isNotEmpty(providerDto.getMemberIds())){
        memberIds.addAll(providerDto.getMemberIds());
      }
      if (providerDto.getId() != null) {
        Provider provider = providerMapper.selectById(providerDto.getId());
        if (provider==null){
          log.info("企业服务商入驻，查询不到此服务商信息!");
          throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此服务商信息!");
        }
        List<ProviderServiceDto> providerServiceDtoList = providerDto.getProviderServiceDtoList();
        if (CollectionUtils.isEmpty(providerServiceDtoList)){
          log.info("会员{}企业服务商入驻,服务类型不能为空",providerDto.getMemberId());
          throw new LogicException(ResultEnum.DATA_ERROR,"企业服务商入驻,服务类型不能为空");
        }
        provider.setVersion(provider.getVersion()+1);
        for (ProviderServiceDto providerServiceDto: providerServiceDtoList){
          ProviderServices providerServices = new ProviderServices();
          if (providerServiceDto.getId()!=null){
            providerServices = providerServicesMapper.selectById(providerServiceDto.getId());
            BeanUtil.copyProperties(providerDto,providerServices);
            providerServices.setVersion(provider.getVersion());
            providerServices.setId(null);
            if (!Objects.equals(providerServices.getValidateState(),ProviderStatEnum.CONFIRM_SUCCESS.getCode())){
              providerServices.setValidateState(ProviderStatEnum.WAIT_CONFIRM.getCode());
            }
          }else {
            BeanUtil.copyProperties(providerServiceDto,providerServices);
            providerServices.setValidateState(ProviderStatEnum.WAIT_CONFIRM.getCode());
            providerServices.setVersion(provider.getVersion());
            providerServices.setUpdateTime(DateUtil.date());
            providerServices.setCreateTime(DateUtil.date());
          }
          providerServicesMapper.insert(providerServices);
        }
        //删除用户
        providerUserMapper.delete(provider.getId());
        for (Long memberId : memberIds){
          ProviderUser providerUser = new ProviderUser();
          providerUser.setMemberId(memberId);
          providerUser.setProviderId(provider.getId());
          providerUser.setCreateTime(DateUtil.date());
          providerUserMapper.insert(providerUser);
        }
      } else {
        Provider provider = new Provider();
        BeanUtil.copyProperties(providerDto, provider);
        provider.setCreateTime(DateUtil.date());
        provider.setCreatorId(providerDto.getMemberId());
        provider.setUpdateTime(DateUtil.date());
        provider.setDisabled(DisabledEnum.ENABLED.getCode());
        provider.setVersion(1);
        if (StringUtils.isNotEmpty(providerDto.getPicPath())) {
          File file = new File(appInfo.getFilePath() + Constant.PROVIDER + member.getId());
          if (!file.exists()) {
            file.mkdirs();
          }
          String srcPath = appInfo.getFilePath() + Constant.CACHE + providerDto.getPicPath();
          String destDir = appInfo.getFilePath() + Constant.PROVIDER + member.getId() + "/";
          Fileutil.cutGeneralFile(srcPath, destDir);
          provider.setPicPath(Constant.PROVIDER + member.getId() + "/" + providerDto.getPicPath());
        }
        providerMapper.insert(provider);
        List<ProviderServiceDto> providerServiceDtoList = providerDto.getProviderServiceDtoList();
        for (ProviderServiceDto providerServiceDto: providerServiceDtoList){
          ProviderServices providerServices = new ProviderServices();
          providerServices.setProviderId(provider.getId());
          providerServices.setServiceId(providerServiceDto.getServiceId());
          providerServices.setValidateState(ProviderStatEnum.WAIT_CONFIRM.getCode());
          providerServices.setCreateTime(DateUtil.date());
          providerServices.setUpdateTime(DateUtil.date());
          providerServices.setVersion(provider.getVersion());
          providerServicesMapper.insert(providerServices);
        }
        for (Long memberId : memberIds){
          ProviderUser providerUser = new ProviderUser();
          providerUser.setMemberId(memberId);
          providerUser.setProviderId(provider.getId());
          providerUser.setCreateTime(DateUtil.date());
          providerUserMapper.insert(providerUser);
        }
      }
    }
  }

  public Page<ProviderVo> findByPage(ProviderQueryDto query) {
    Page<ProviderVo> page = commonService.find(query, (p, q) ->
      providerMapper.findByPage(p, BeanUtil.beanToMap(query)));
    page.setRecords(page.getRecords().stream().collect(Collectors.toList())
    );
    return page;
  }

  @Transactional
  public void confirmProvider(ConfirmProviderDto confirmProviderDto) {
    Provider provider = providerMapper.selectById(confirmProviderDto.getId());
    if (provider == null || Objects.equals(provider.getValidateState(), ProviderStatEnum.WAIT_CONFIRM.getCode())) {
      log.info("服务商id:{} 服务商入驻信息不存在,或非待审核状态!", confirmProviderDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR, "服务商查询不到或非待审核状态!");
    }

    List<ProviderServicesConfirmDto> providerServicesConfirmDtoList = confirmProviderDto.getProviderServicesConfirmDtoList();
    if (CollectionUtils.isEmpty(providerServicesConfirmDtoList)){
      log.info("服务商{} ,服务商认证详情不能为空!",confirmProviderDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"资质认证详情不能为空!");
    }
    Integer validateState = ValidateStatEnum.CONFIRM_SUCCESS.getCode();
    for (ProviderServicesConfirmDto providerServicesConfirmDto: providerServicesConfirmDtoList){
      ProviderServices providerServices = providerServicesMapper.selectById(confirmProviderDto.getId());
      if (providerServices==null){
        log.info("服务商类型查询不到此服务类型");
        throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此服务类型!");
      }
      providerServices.setUpdateTime(DateUtil.date());
      if (providerServicesConfirmDto.isAgree()) {
        providerServices.setValidateState(ValidateStatEnum.CONFIRM_SUCCESS.getCode());
      } else {
        validateState = ValidateStatEnum.CONFIRM_FAILED.getCode();
        providerServices.setValidateState(ValidateStatEnum.CONFIRM_FAILED.getCode());
      }
      providerServices.setValidateText(providerServicesConfirmDto.getValidateText());
      providerServices.setValidateTime(DateUtil.date());
      providerServices.setValidatorId(confirmProviderDto.getValidatorId());
      providerServicesMapper.updateById(providerServices);
    }

    List<ProviderServicesVo> providerServicesVoList = providerServicesMapper.findProviderServicesVoListByProviderId(confirmProviderDto.getId());
    Integer providerValidateStatus = ValidateStatEnum.CONFIRM_SUCCESS.getCode();
    for (ProviderServicesVo providerServicesVo: providerServicesVoList){
      if (Objects.equals(providerServicesVo.getValidateState(),ValidateStatEnum.WAIT_CONFIRM.getCode())){
        providerValidateStatus = ValidateStatEnum.WAIT_CONFIRM.getCode();
        break;
      }
      if (Objects.equals(providerServicesVo.getValidateState(),ValidateStatEnum.CONFIRM_FAILED.getCode())){
        providerValidateStatus = ValidateStatEnum.CONFIRM_FAILED.getCode();
        break;
      }
    }
    provider.setValidateState(providerValidateStatus);
    providerMapper.updateById(provider);
  }
}
