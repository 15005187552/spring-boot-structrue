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
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberPaper;
import com.ljwm.gecko.base.entity.Provider;
import com.ljwm.gecko.base.entity.ProviderPaper;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.ProviderStatEnum;
import com.ljwm.gecko.base.enums.ValidateStatEnum;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.mapper.MemberPaperMapper;
import com.ljwm.gecko.base.mapper.ProviderMapper;
import com.ljwm.gecko.base.mapper.ProviderPaperMapper;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.ConfirmProviderDto;
import com.ljwm.gecko.base.model.dto.FileDto;
import com.ljwm.gecko.base.model.dto.ProviderDto;
import com.ljwm.gecko.base.model.dto.ProviderQueryDto;
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
  private AppInfo appInfo;

  @Autowired
  private ProviderPaperMapper providerPaperMapper;

  @Autowired
  private CommonService commonService;

  @Transactional
  public void saveProvider(ProviderDto providerDto) {
    Member member = memberMapper.selectById(providerDto.getMemberId());
    if (member != null && !Objects.equals(member.getValidateState(), ValidateStatEnum.CONFIRM_SUCCESS.getCode())) {

      List<MemberPaper> memberPaperList = memberPaperMapper.selectByMap(Kv.by("MEMBER_ID",member.getId()));
      if (CollectionUtils.isEmpty(providerDto.getMemberIds())) {
        log.info("会员id：{} 没有添加资质认证人!", providerDto.getMemberId());
        throw new LogicException(ResultEnum.DATA_ERROR, "请添加资质认证人!");
      }
    }
    List<Long> memberIds = Lists.newArrayList();
    memberIds.add(providerDto.getMemberId());
    if (CollectionUtils.isNotEmpty(providerDto.getMemberIds())) {
      for (Long memberId : providerDto.getMemberIds()) {
        memberIds.add(memberId);
        Member tempMember = memberMapper.selectById(memberId);
        if (tempMember == null || !Objects.equals(tempMember.getValidateState(), ValidateStatEnum.CONFIRM_SUCCESS.getCode())) {
          log.info("会员id：{} 没有进行资质认证,请重新添加!", tempMember.getId());
          throw new LogicException(ResultEnum.DATA_ERROR, "会员没有进行资质认证,请重新添加!");
        }
      }
    }
    //插入服务商表
    Provider provider = new Provider();
    BeanUtil.copyProperties(providerDto, provider);
    provider.setCreateTime(DateUtil.date());
    provider.setCreatorId(providerDto.getMemberId());
    provider.setUpdateTime(DateUtil.date());
    provider.setDisabled(DisabledEnum.ENABLED.getCode());
    if (StringUtils.isNotEmpty(providerDto.getPicPath())){
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
  }

  public Page<ProviderVo> findByPage(ProviderQueryDto query) {
    Page<ProviderVo> page = commonService.find(query, (p, q) ->
      providerMapper.findByPage(p, BeanUtil.beanToMap(query)));
    page.setRecords(page.getRecords().stream().map(temp ->
      temp.setServiceTypeTrees(
        ServiceTypeTree.createServiceTree(temp.getServiceTypes())
      )).collect(Collectors.toList())
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
    if (confirmProviderDto.isAgree()) {
      provider.setValidateState(ProviderStatEnum.CONFIRM_SUCCESS.getCode());
    } else {
      provider.setValidateState(ProviderStatEnum.CONFIRM_FAILED.getCode());
    }
    provider.setValidatorId(confirmProviderDto.getValidaterId());
    provider.setValidateText(confirmProviderDto.getValidateText());
    provider.setValidateTime(DateUtil.date());
    providerMapper.updateById(provider);
  }
}
