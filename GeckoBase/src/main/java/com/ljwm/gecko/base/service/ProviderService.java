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
import com.ljwm.gecko.base.enums.*;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.model.vo.*;
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

  public Page<ProviderVo> findByPage(ProviderQueryDto query) {
    Page<ProviderVo> page = commonService.find(query, (p, q) ->
      providerMapper.findByPage(p, BeanUtil.beanToMap(query)));
    page.setRecords(page.getRecords().stream().collect(Collectors.toList())
    );
    return page;
  }

  @Transactional
  public List<ProviderServicesVo> confirmProvider(ConfirmProviderDto confirmProviderDto) {
    Provider provider = providerMapper.selectById(confirmProviderDto.getId());
    if (provider == null || !Objects.equals(provider.getValidateState(), ProviderStatEnum.WAIT_CONFIRM.getCode())) {
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
      ProviderServices providerServices = providerServicesMapper.selectById(providerServicesConfirmDto.getId());
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
    }
    if (!Objects.equals(providerValidateStatus,ValidateStatEnum.WAIT_CONFIRM.getCode())){
        for (ProviderServicesVo providerServicesVo: providerServicesVoList){
          if (Objects.equals(providerServicesVo.getValidateState(),ValidateStatEnum.CONFIRM_FAILED.getCode())){
            providerValidateStatus = ValidateStatEnum.CONFIRM_FAILED.getCode();
            break;
          }
        }
    }
    provider.setValidateState(providerValidateStatus);
    providerMapper.updateById(provider);
    List<ProviderServicesVo> servicesVoList = providerServicesMapper.findProviderServicesVoListByProviderId(provider.getId());
    return servicesVoList;
  }

  @Transactional
  public void confirmProviderInfo(ConfirmProviderInfoDto confirmProviderInfoDto){
    Provider provider = providerMapper.selectById(confirmProviderInfoDto.getId());
    if (provider == null ) {
      log.info("服务商id:{} 服务商入驻信息不存在", confirmProviderInfoDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR, "服务商查询不到");
    }
    if (!Objects.equals(provider.getInfoValidateState(), InfoValidateStateEnum.INIT.getCode())){
      log.info("服务商id{},非待审核基本信息状态!",confirmProviderInfoDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"非待审核基本信息状态!");
    }
    if (confirmProviderInfoDto.isAgree()){
      provider.setInfoValidateState(InfoValidateStateEnum.CONFIRM_SUCCESS.getCode());
    }else {
      provider.setInfoValidateState(InfoValidateStateEnum.CONFIRM_FAILED.getCode());
      provider.setValidateText(confirmProviderInfoDto.getValidateText());
    }
    provider.setValidatorId(confirmProviderInfoDto.getValidatorId());
    providerMapper.updateById(provider);
  }


}
