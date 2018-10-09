package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.mapper.CommonMapper;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.Provider;
import com.ljwm.gecko.base.entity.ProviderServices;
import com.ljwm.gecko.base.entity.ProviderUser;
import com.ljwm.gecko.base.enums.*;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.ProviderDto;
import com.ljwm.gecko.base.model.dto.ProviderQueryDto;
import com.ljwm.gecko.base.model.dto.ProviderServiceDto;
import com.ljwm.gecko.base.model.vo.MemberPaperVo;
import com.ljwm.gecko.base.model.vo.ProviderSimpleVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.base.utils.Fileutil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientProviderService {

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
        if (StringUtils.isNotEmpty(providerDto.getLogo())){
          if (!providerDto.getLogo().contains(Constant.PROVIDER)){
            File file = new File(appInfo.getFilePath() + Constant.PROVIDER + member.getId());
            if (!file.exists()) {
              file.mkdirs();
            }
            String srcPath = appInfo.getFilePath() + Constant.CACHE + providerDto.getLogo();
            String destDir = appInfo.getFilePath() + Constant.PROVIDER + member.getId() + "/";
            Fileutil.cutGeneralFile(srcPath, destDir);
            provider.setLogo(Constant.PROVIDER + member.getId() + "/" + providerDto.getLogo());
          }
        }
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
        provider.setCreateTime(DateUtil.date());
        provider.setCreatorId(providerDto.getMemberId());
        provider.setUpdateTime(DateUtil.date());
        provider.setDisabled(DisabledEnum.ENABLED.getCode());
        provider.setValidateState(ValidateStatEnum.WAIT_CONFIRM.getCode());
        if (StringUtils.isNotEmpty(providerDto.getLogo())){
          if (!providerDto.getLogo().contains(Constant.PROVIDER)){
            File file = new File(appInfo.getFilePath() + Constant.PROVIDER + member.getId());
            if (!file.exists()) {
              file.mkdirs();
            }
            String srcPath = appInfo.getFilePath() + Constant.CACHE + providerDto.getLogo();
            String destDir = appInfo.getFilePath() + Constant.PROVIDER + member.getId() + "/";
            Fileutil.cutGeneralFile(srcPath, destDir);
            provider.setLogo(Constant.PROVIDER + member.getId() + "/" + providerDto.getLogo());
          }
        }
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
        BeanUtil.copyProperties(providerDto,provider);
        List<ProviderServiceDto> providerServiceDtoList = providerDto.getProviderServiceDtoList();
        if (CollectionUtils.isEmpty(providerServiceDtoList)){
          log.info("会员{}企业服务商入驻,服务类型不能为空",providerDto.getMemberId());
          throw new LogicException(ResultEnum.DATA_ERROR,"企业服务商入驻,服务类型不能为空");
        }
        provider.setVersion(provider.getVersion()+1);
        if (StringUtils.isNotEmpty(providerDto.getPicPath())) {
          if (!providerDto.getPicPath().contains(Constant.PROVIDER)){
            File file = new File(appInfo.getFilePath() + Constant.PROVIDER + member.getId());
            if (!file.exists()) {
              file.mkdirs();
            }
            String srcPath = appInfo.getFilePath() + Constant.CACHE + providerDto.getPicPath();
            String destDir = appInfo.getFilePath() + Constant.PROVIDER + member.getId() + "/";
            Fileutil.cutGeneralFile(srcPath, destDir);
            provider.setPicPath(Constant.PROVIDER + member.getId() + "/" + providerDto.getPicPath());
            provider.setInfoValidateState(0);
            provider.setValidateText(StringUtils.EMPTY);
          }
        }
        if (StringUtils.isNotEmpty(providerDto.getLogo())){
          if (!providerDto.getLogo().contains(Constant.PROVIDER)){
            File file = new File(appInfo.getFilePath() + Constant.PROVIDER + member.getId());
            if (!file.exists()) {
              file.mkdirs();
            }
            String srcPath = appInfo.getFilePath() + Constant.CACHE + providerDto.getLogo();
            String destDir = appInfo.getFilePath() + Constant.PROVIDER + member.getId() + "/";
            Fileutil.cutGeneralFile(srcPath, destDir);
            provider.setLogo(Constant.PROVIDER + member.getId() + "/" + providerDto.getLogo());
          }
        }
        for (ProviderServiceDto providerServiceDto: providerServiceDtoList){
          ProviderServices providerServices = new ProviderServices();
          if (providerServiceDto.getId()!=null){
            providerServices = providerServicesMapper.selectById(providerServiceDto.getId());
            BeanUtil.copyProperties(providerDto,providerServices);
            providerServices.setVersion(provider.getVersion());
            providerServices.setProviderId(providerDto.getId());
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
            providerServices.setProviderId(provider.getId());
          }
          providerServicesMapper.insert(providerServices);
        }
        //删除用户
        providerUserMapper.delete(provider.getId());
        List<ProviderUser> providerUserList = providerUserMapper.selectByMap(Kv.by("PROVIDER_ID",provider.getId()));
        List<Long> oldMemberIds = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(providerUserList)){
          for (ProviderUser providerUser: providerUserList){
            oldMemberIds.add(providerUser.getMemberId());
          }
        }
        //现在服务商所拥有的报税员
        List<Long> addMemberIds = Lists.newArrayList();
        addMemberIds.addAll(memberIds);
        //获取并集并不重复
        oldMemberIds.removeAll(memberIds);
        memberIds.addAll(oldMemberIds);
        for (Long memberId : memberIds){
          ProviderUser providerUser = providerUserMapper.findByMap(Kv.by("providerId",provider.getId()).set("memberId",memberId));
          if (providerUser==null){
            ProviderUser providerUserTemp = new ProviderUser();
            providerUserTemp.setMemberId(memberId);
            providerUserTemp.setProviderId(provider.getId());
            providerUserTemp.setCreateTime(DateUtil.date());
            if (Objects.equals(memberId,providerDto.getMemberId())){
              providerUserTemp.setRolesCode(ProviderRoleEnum.CREATOR.getCode()+","+ProviderRoleEnum.PREPARER.getCode());
            }else {
              providerUserTemp.setRolesCode(String.valueOf(ProviderRoleEnum.PREPARER.getCode()));
            }
            providerUserMapper.insert(providerUserTemp);
          }else {
            //服务商所拥有的会员
            if (addMemberIds.contains(memberId)){
              //已经有报税员角色
              if (!providerUser.getRolesCode().contains(String.valueOf(ProviderRoleEnum.PREPARER.getCode()))){
                providerUser.setRolesCode(providerUser.getRolesCode()+","+ProviderRoleEnum.PREPARER.getCode());
                providerUserMapper.updateById(providerUser);
              }
            }else {
                //删除的角色
              if (providerUser.getRolesCode().contains(String.valueOf(ProviderRoleEnum.PREPARER.getCode()))){
                  String [] roles = providerUser.getRolesCode().split("\\,");
                  List<String> list=Arrays.asList(roles);
                  list.remove(String.valueOf(ProviderRoleEnum.PREPARER.getCode()));
                  if (CollectionUtils.isEmpty(list)){
                    providerUserMapper.deleteById(providerUser.getId());
                  }else {
                    String roleNew = Joiner.on(",").join(list);
                    providerUser.setRolesCode(roleNew);
                    providerUserMapper.updateById(providerUser);
                  }
              }
            }
          }
        }
        providerMapper.updateById(provider);
      } else {
        Provider provider = new Provider();
        BeanUtil.copyProperties(providerDto, provider);
        provider.setCreateTime(DateUtil.date());
        provider.setCreatorId(providerDto.getMemberId());
        provider.setUpdateTime(DateUtil.date());
        provider.setDisabled(DisabledEnum.ENABLED.getCode());
        provider.setValidateState(ValidateStatEnum.WAIT_CONFIRM.getCode());
        provider.setVersion(1);
        if (StringUtils.isNotEmpty(providerDto.getPicPath())) {
          if (!providerDto.getPicPath().contains(Constant.PROVIDER)){
            File file = new File(appInfo.getFilePath() + Constant.PROVIDER + member.getId());
            if (!file.exists()) {
              file.mkdirs();
            }
            String srcPath = appInfo.getFilePath() + Constant.CACHE + providerDto.getPicPath();
            String destDir = appInfo.getFilePath() + Constant.PROVIDER + member.getId() + "/";
            Fileutil.cutGeneralFile(srcPath, destDir);
            provider.setPicPath(Constant.PROVIDER + member.getId() + "/" + providerDto.getPicPath());
          }
        }
        if (StringUtils.isNotEmpty(providerDto.getLogo())){
          if (!providerDto.getLogo().contains(Constant.PROVIDER)){
            File file = new File(appInfo.getFilePath() + Constant.PROVIDER + member.getId());
            if (!file.exists()) {
              file.mkdirs();
            }
            String srcPath = appInfo.getFilePath() + Constant.CACHE + providerDto.getLogo();
            String destDir = appInfo.getFilePath() + Constant.PROVIDER + member.getId() + "/";
            Fileutil.cutGeneralFile(srcPath, destDir);
            provider.setLogo(Constant.PROVIDER + member.getId() + "/" + providerDto.getLogo());
          }
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
          if (Objects.equals(memberId,providerDto.getMemberId())){
            providerUser.setRolesCode(ProviderRoleEnum.CREATOR.getCode()+","+ProviderRoleEnum.PREPARER.getCode());
          }else {
            providerUser.setRolesCode(String.valueOf(ProviderRoleEnum.PREPARER.getCode()));
          }
          providerUserMapper.insert(providerUser);
        }
      }
    }
  }

  public ProviderVo findProviderByMemberId(Long memberId){
    return providerMapper.findProviderByMemberId(memberId);
  }

  public ProviderVo findProviderByProviderId(Long providerId){
    return providerMapper.findProviderByProviderId(providerId);
  }

  public Page<ProviderSimpleVo> findClientByPage(ProviderQueryDto query) {
    Page<ProviderSimpleVo> page = commonService.find(query, (p, q) ->
      providerMapper.findClientByPage(p, BeanUtil.beanToMap(query)));
    page.setRecords(page.getRecords().stream().collect(Collectors.toList())
    );
    return page;
  }
}
