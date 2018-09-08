package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.dto.SqlFactory;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.mapper.CommonMapper;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.Provider;
import com.ljwm.gecko.base.entity.ProviderPaper;
import com.ljwm.gecko.base.enums.ValidateStatEnum;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.mapper.ProviderMapper;
import com.ljwm.gecko.base.mapper.ProviderPaperMapper;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.FileDto;
import com.ljwm.gecko.base.model.dto.ProviderDto;
import com.ljwm.gecko.base.utils.Fileutil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
public class ProviderService {

  @Autowired
  private ProviderMapper providerMapper;

  @Autowired
  private MemberMapper memberMapper;

  @Autowired
  private CommonMapper commonMapper;

  @Autowired
  private AppInfo appInfo;

  @Autowired
  private ProviderPaperMapper providerPaperMapper;

  @Transactional
  public void saveProvider(ProviderDto providerDto){
    Member member = memberMapper.selectById(providerDto.getMemberId());
    if (member!=null&& !Objects.equals(member.getValidateState(),ValidateStatEnum.CONFIRM_SUCCESS.getCode())){
      if (CollectionUtils.isEmpty(providerDto.getMemberIds())){
        log.info("会员id：{} 没有添加资质认证人!",providerDto.getMemberId());
        throw new LogicException(ResultEnum.DATA_ERROR,"请添加资质认证人!");
      }
    }
    if (CollectionUtils.isEmpty(providerDto.getServiceIds())){
      log.info("会员id:{} 服务类型不能为空!",providerDto.getMemberId());
      throw new LogicException(ResultEnum.DATA_ERROR,"服务类型不能为空!");
    }
    if (CollectionUtils.isEmpty(providerDto.getFileDtoList())){
      log.info("会员id:{} 资质类型附件不能为空!",providerDto.getMemberId());
      throw new LogicException(ResultEnum.DATA_ERROR,"资质类型附件不能为空!");
    }
    List<Long> memberIds = Lists.newArrayList();
    memberIds.add(providerDto.getMemberId());
    if (CollectionUtils.isNotEmpty(providerDto.getMemberIds())){
      for (Long memberId : providerDto.getMemberIds()){
        memberIds.add(memberId);
        Member tempMember = memberMapper.selectById(memberId);
        if (tempMember==null || !Objects.equals(tempMember.getValidateState(),ValidateStatEnum.CONFIRM_SUCCESS.getCode())){
          log.info("会员id：{} 没有进行资质认证,请重新添加!",tempMember.getId());
          throw new LogicException(ResultEnum.DATA_ERROR,"会员没有进行资质认证,请重新添加!");
        }
      }
    }
    //插入服务商表
    Provider provider = new Provider();
    BeanUtil.copyProperties(providerDto,provider);
    provider.setCreateTime(DateUtil.date());
    provider.setCreaterId(providerDto.getMemberId());
    provider.setUpdateTime(DateUtil.date());
    providerMapper.insert(provider);

    //插入服务商服务表
    commonMapper.batchInsert(
      Kv.by(SqlFactory.TABLE, "t_provider_service")
        .set(SqlFactory.COLUMNS, new String[]{"SERVICE_ID", "PROVIDER_ID"})
        .set(SqlFactory.VALUES, new HashSet<>(providerDto.getServiceIds()).stream().map(roleId -> new String[]{roleId + "", provider.getId() + ""}).collect(Collectors.toList()))
    );
    //执行资质文件入库操作
    File file = new File(appInfo.getFilePath()+ Constant.PROVIDER+ member.getId());
    if(!file.exists()){
      file.mkdirs();
    }
    for (FileDto fileDto: providerDto.getFileDtoList()){
      String srcPath = appInfo.getFilePath() + Constant.CACHE + fileDto.getFileName();
      String destDir = appInfo.getFilePath()+Constant.PROVIDER + member.getId()+ "/";
      Fileutil.cutGeneralFile(srcPath, destDir);
      ProviderPaper providerPaper = new ProviderPaper();
      providerPaper.setProviderId(provider.getId());
      providerPaper.setPaperId(fileDto.getPaperId());
      providerPaper.setPicPath(Constant.PROVIDER + member.getId() + "/" + fileDto.getFileName());
      providerPaper.setCreateTime(DateUtil.date());
      providerPaper.setUpdateTime(DateUtil.date());
      providerPaperMapper.insert(providerPaper);
    }

  }
}
