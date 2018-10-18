package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
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
import com.ljwm.gecko.base.model.dto.im.MessageDto;
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
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class ProviderService {

  @Autowired
  private ProviderServicesMapper providerServicesMapper;

  @Autowired
  private ProviderPaperMapper providerPaperMapper;

  @Autowired
  private ProviderUserMapper providerUserMapper;

  @Autowired
  private MemberPaperMapper memberPaperMapper;

  @Autowired
  private MessageService messageService;

  @Autowired
  private ProviderMapper providerMapper;

  @Autowired
  private CommonService commonService;

  @Autowired
  private MemberMapper memberMapper;

  @Autowired
  private CommonMapper commonMapper;

  @Autowired
  private AppInfo appInfo;


  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private OrderCommentsMapper orderCommentsMapper;

  public Page<ProviderVo> findByPage(ProviderQueryDto query) {
    Page<ProviderVo> page = commonService.find(query,(p,q) ->
      providerMapper.findByPage(p,BeanUtil.beanToMap(query)));
    page.setRecords(page.getRecords().stream().collect(Collectors.toList())
    );
    return page;
  }

  @Transactional
  public List<ProviderServicesVo> confirmProvider(ConfirmProviderDto confirmProviderDto) {
    Provider provider = providerMapper.selectById(confirmProviderDto.getId());
    if (provider == null || !Objects.equals(provider.getInfoValidateState(),InfoValidateStateEnum.CONFIRM_SUCCESS.getCode())) {
      log.info("服务商id:{} 服务商入驻信息不存在,或基本信息为非已审核状态!",confirmProviderDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"服务商查询不到或基本信息为非已审核状态!");
    }
    List<ProviderServicesConfirmDto> providerServicesConfirmDtoList = confirmProviderDto.getProviderServicesConfirmDtoList();
    if (CollectionUtils.isEmpty(providerServicesConfirmDtoList)) {
      log.info("服务商{} ,服务商认证详情不能为空!",confirmProviderDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"资质认证详情不能为空!");
    }

    for (ProviderServicesConfirmDto providerServicesConfirmDto : providerServicesConfirmDtoList) {
      Integer validateState = ValidateStatEnum.CONFIRM_SUCCESS.getCode();
      ProviderServices providerServices = providerServicesMapper.selectById(providerServicesConfirmDto.getId());
      if (providerServices == null) {
        log.info("服务商类型查询不到此服务类型");
        throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此服务类型!");
      }
      providerServices.setUpdateTime(DateUtil.date());
      if (providerServicesConfirmDto.isAgree()) {
        providerServices.setValidateState(ValidateStatEnum.CONFIRM_SUCCESS.getCode());
        providerServices.setValidateText(StringUtils.EMPTY);
      } else {
        validateState = ValidateStatEnum.CONFIRM_FAILED.getCode();
        providerServices.setValidateState(ValidateStatEnum.CONFIRM_FAILED.getCode());
        providerServices.setValidateText(providerServicesConfirmDto.getValidateText());
      }
      providerServices.setValidateTime(DateUtil.date());
      providerServices.setValidatorId(confirmProviderDto.getValidatorId());
      providerServicesMapper.updateById(providerServices);
    }

    List<ProviderServicesVo> providerServicesVoList = providerServicesMapper.findProviderServicesVoListByProviderId(confirmProviderDto.getId());
    Integer providerValidateStatus = ValidateStatEnum.CONFIRM_SUCCESS.getCode();
    for (ProviderServicesVo providerServicesVo : providerServicesVoList) {
      if (Objects.equals(providerServicesVo.getValidateState(),ValidateStatEnum.WAIT_CONFIRM.getCode())) {
        providerValidateStatus = ValidateStatEnum.WAIT_CONFIRM.getCode();
        break;
      }
    }
    if (!Objects.equals(providerValidateStatus,ValidateStatEnum.WAIT_CONFIRM.getCode())) {
      for (ProviderServicesVo providerServicesVo : providerServicesVoList) {
        if (Objects.equals(providerServicesVo.getValidateState(),ValidateStatEnum.CONFIRM_FAILED.getCode())) {
          providerValidateStatus = ValidateStatEnum.CONFIRM_FAILED.getCode();
          break;
        }
      }
    }
    provider.setValidateState(providerValidateStatus);
    providerMapper.updateById(provider);
    List<ProviderServicesVo> servicesVoList = providerServicesMapper.findProviderServicesVoListByProviderId(provider.getId());
    if (!Objects.equals(provider.getValidateState(),ProviderStatEnum.WAIT_CONFIRM.getCode()))
      pushMessage(provider,servicesVoList);
    return servicesVoList;
  }

  @Transactional
  public void confirmProviderInfo(ConfirmProviderInfoDto confirmProviderInfoDto) {
    Provider provider = providerMapper.selectById(confirmProviderInfoDto.getId());
    if (provider == null) {
      log.info("服务商id:{} 服务商入驻信息不存在",confirmProviderInfoDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"服务商查询不到");
    }
    if (!Objects.equals(provider.getInfoValidateState(),InfoValidateStateEnum.INIT.getCode())) {
      log.info("服务商id{},非待审核基本信息状态!",confirmProviderInfoDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"非待审核基本信息状态!");
    }
    if (confirmProviderInfoDto.isAgree()) {
      provider.setInfoValidateState(InfoValidateStateEnum.CONFIRM_SUCCESS.getCode());
      provider.setValidateText(StringUtils.EMPTY);
    } else {
      provider.setInfoValidateState(InfoValidateStateEnum.CONFIRM_FAILED.getCode());
      provider.setValidateText(confirmProviderInfoDto.getValidateText());
    }
    provider.setValidatorId(confirmProviderInfoDto.getValidatorId());
    providerMapper.updateById(provider);

    // 基础信息审核失败发送消息
    if (Objects.equals(provider.getInfoValidateState(),InfoValidateStateEnum.CONFIRM_FAILED.getCode()))
      pushInfoMessage(provider);
  }

  /**
   * 服务商基本信息审核消息推送
   *
   * @param provider
   */
  public void pushInfoMessage(Provider provider) {
    // 1.bean 实例化
    MessageDto messageDto = new MessageDto();
    JSONObject message = new JSONObject();
    // 2.写入参数
    message.put("templateString",MPTemplateEnum.AUDIT.getName());
    message.put("wxParams",
      Kv.by("keyword1",provider.getName()).set("keyword2",InfoValidateStateEnum.getName(provider.getInfoValidateState()))
        .set("keyword3",provider.getValidateText()) // 这里的validateText 是基本信息审核失败的内容
    );
    messageDto.setLoginType(Collections.singletonList(LoginType.WX_APP))
      .setReceiverId(provider.getCreatorId())
      .setMessage(message.toJSONString());
    // 3.发送消息
    log.info("发送服务商基本信息审核消息");
    messageService.pushMessage(messageDto);
  }

  /**
   * 服务商内容审核结果消息推送
   *
   * @param provider
   * @param servicesVoList
   */
  public void pushMessage(Provider provider,List<ProviderServicesVo> servicesVoList) {
    // 1.获取审核失败内容
    List<ProviderServicesVo> fails = servicesVoList.stream()
      .filter(item -> Objects.equals(item.getValidateState(),ProviderStatEnum.CONFIRM_FAILED.getCode()))
      .collect(Collectors.toList());
    StringBuffer temp = new StringBuffer();
    if (fails.size() > 0) {
      temp.append("失败服务内容：");
      for (ProviderServicesVo providerServicesVo : fails) {
        temp.append(providerServicesVo.getServeSimpleVo().getName());
        temp.append(",");
      }
      temp.substring(temp.length() - 1,temp.length());
    }
    // 2.bean实例化
    MessageDto messageDto = new MessageDto();
    JSONObject message = new JSONObject();
    // 3.写入参数
    message.put("templateString",MPTemplateEnum.AUDIT.getName());
    message.put("wxParams",
      Kv.by("keyword1",provider.getName()).set("keyword2",ProviderStatEnum.getName(provider.getValidateState()))
        .set("keyword3",temp.toString())
    );
    messageDto.setLoginType(Collections.singletonList(LoginType.WX_APP))
      .setReceiverId(provider.getCreatorId())
      .setMessage(message.toJSONString());
    // 4.发送消息
    log.info("发送服务商审核消息");
    messageService.pushMessage(messageDto);
  }


  public Provider saveCashDeposit(Long providerId,BigDecimal cashDeosit) {
    Provider provider = providerMapper.selectById(providerId);
    if (provider == null) throw new LogicException(ResultEnum.DATA_ERROR,"未找到id为" + providerId + "的服务商");
    provider.setCashDeposit(cashDeosit);
    providerMapper.updateById(provider);
    return provider;
  }

  public Integer starCount(Long providerId) {
    Integer totalOrderCount = orderMapper.findProviderOrderCount(providerId);
    if (Objects.equals(0,totalOrderCount)) {
      return 0;
    }
    Integer totalStarCount = orderCommentsMapper.starCount(providerId);
    return totalStarCount / totalOrderCount;
  }
}
