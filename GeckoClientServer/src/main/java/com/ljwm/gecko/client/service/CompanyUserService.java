package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.enums.ActivateEnum;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.dto.MemberComForm;
import com.ljwm.gecko.base.model.vo.CompanyVo;
import com.ljwm.gecko.base.model.vo.PersonInfoVo;
import com.ljwm.gecko.client.dao.CompanyUserDao;
import com.ljwm.gecko.client.model.dto.*;
import com.ljwm.gecko.client.model.vo.CompanyInfoVo;
import com.ljwm.gecko.client.model.vo.CompanyUserVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/8 17:49
 */
@Service
public class CompanyUserService {

  @Autowired
  CompanyUserDao companyUserDao;

  @Autowired
  CompanyUserMapper companyUserMapper;

  @Autowired
  CompanyMapper companyMapper;

  @Autowired
  NaturalPersonMapper naturalPersonMapper;

  @Autowired
  CompanyService companyService;

  @Autowired
  MemberMapper memberMapper;

  @Autowired
  CompanyUserInfoMapper companyUserInfoMapper;

  @Autowired
  CommonService commonService;

  @Transactional
  public Result memberEnterCom(MemberComForm memberComForm) {
    companyUserDao.insertOrUpdate(memberComForm.getMemberId(), memberComForm.getCompanyId(), memberComForm.getRoleCode());
    return Result.success("成功！");
  }

  public Result findCompany() {
    Long memberId = SecurityKit.currentId();
    NaturalPerson naturalPerson = naturalPersonMapper.selectOne(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.MEMBER_ID,memberId));
    Long companyId = null;
    if (naturalPerson != null){
      companyId = naturalPerson.getCompanyId();
    }
    List<Company> list = companyMapper.findCompany(memberId);
    if(CollectionUtil.isNotEmpty(list)){
      List<CompanyVo> companyVoList = new ArrayList<>();
      for (Company company: list) {
        CompanyInfoVo companyInfoVo = new CompanyInfoVo();
        BeanUtil.copyProperties(company, companyInfoVo);
        companyInfoVo.setFlag(false);
        if (companyId != null && companyId.equals(companyInfoVo.getId())){
          companyInfoVo.setFlag(true);
        }
        companyVoList.add(companyInfoVo);
      }
      return Result.success(companyVoList);
    }
    return Result.success(null);
  }

  public Result inactiveCompany(MemberForm memberForm) {
    Map<String, Object> map = new HashedMap();
    map.put("MEMBER_ID", memberForm.getMemberId());
    map.put("ACTIATED", ActivateEnum.DISABLED);
    List<CompanyUser> list = companyUserMapper.selectByMap(map);
    return Result.success(list);
  }

  @Transactional
  public Result enterCompany(InactiveForm inactiveForm) {
    Long memberId = SecurityKit.currentId();
    Map<String, Object> map = new HashedMap();
    map.put("COMPANY_ID", inactiveForm.getCompanyId());
    map.put("MEMBER_ID", memberId);
    List<CompanyUser> list = companyUserMapper.selectByMap(map);
    if(CollectionUtil.isEmpty(list)){
      return Result.fail("传入的参数有误!");
    }
    CompanyUser companyUser = list.get(0);
    if(inactiveForm.isFlag()){
      NaturalPerson naturalPerson = naturalPersonMapper.selectById(memberId);
      if(naturalPerson != null) {
        naturalPerson.setCompanyId(inactiveForm.getCompanyId());
        naturalPersonMapper.updateById(naturalPerson);
      }
    }
    companyUser.setActivated(ActivateEnum.ENABLED.getCode());
    companyUserMapper.updateById(companyUser);
    return Result.success("成功！");
  }

  public Result taxCompany() {
    Long memberId = SecurityKit.currentId();
    NaturalPerson naturalPerson = naturalPersonMapper.selectOne(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.MEMBER_ID, memberId));
    if(naturalPerson != null){
      return Result.success(companyService.findCompanyById(naturalPerson.getCompanyId()));
    }
//    throw new LogicException("该用户不是会员");
    return Result.success(null);
  }

  public Result roleInCompany(CompanyDto companyDto) {
    Long memberId = SecurityKit.currentId();
    CompanyUser companyUser = companyUserMapper.selectOne(new QueryWrapper<CompanyUser>().eq(CompanyUser.MEMBER_ID, memberId)
      .eq(CompanyUser.COMPANY_ID, companyDto.getCompanyId()).eq(CompanyUser.DISABLED, DisabledEnum.ENABLED.getCode())
      .eq(CompanyUser.ACTIVATED, ActivateEnum.ENABLED.getCode()));
    if(companyUser != null){
      return Result.success(companyUser.getRolesCode());
    }
//    throw new LogicException("公司ID有误！");
    return Result.success(null);
  }

  public Result memberRoleList(CompanyDto companyDto) {
    List<CompanyUser> list = companyUserMapper.selectList(new QueryWrapper<CompanyUser>().eq(CompanyUser.COMPANY_ID, companyDto.getCompanyId()).
      eq(CompanyUser.DISABLED, DisabledEnum.ENABLED.getCode()).eq(CompanyUser.ACTIVATED, ActivateEnum.ENABLED.getCode()));
    List<CompanyUserVo> companyUserVoList = new ArrayList<>();
    for (CompanyUser companyUser:
         list) {
      CompanyUserVo companyUserVo = new CompanyUserVo();
      BeanUtil.copyProperties(companyUser, companyUserVo);
      companyUserVo.setName(naturalPersonMapper.selectById(companyUser.getMemberId()).getName());
      Member member = memberMapper.selectById(companyUser.getMemberId());
      companyUserVo.setNickName(member.getNickName());
      companyUserVo.setAvatarPath(member.getAvatarPath());
      companyUserVoList.add(companyUserVo);
    }
    if (CollectionUtil.isNotEmpty(companyUserVoList)){
      return Result.success(companyUserVoList);
    }
//    throw new LogicException("该公司不存在!");
    return Result.success(null);
  }

  public Result memberRole(MemberIdDto memberIdDto) {
    return Result.success(companyUserMapper.selectList(new QueryWrapper<CompanyUser>()
      .eq(CompanyUser.MEMBER_ID, memberIdDto.getMemberId())
      .eq(CompanyUser.DISABLED, DisabledEnum.ENABLED.getCode())
      .eq(CompanyUser.ACTIVATED, ActivateEnum.ENABLED.getCode())));
  }

  public Result findEmployeeInfo(CompanyPageDto companyPageDto) {
    Page<PersonInfoVo> page = commonService.find(companyPageDto, (p, q)->naturalPersonMapper.findList(p, companyPageDto.getCompanyId()));
    List<PersonInfoVo> personInfoDtoList = page.getRecords();
    for (PersonInfoVo personInfoVo:personInfoDtoList) {
      List<CompanyUserInfo> companyUserInfoList = companyUserInfoMapper.selectCompanyUser(companyPageDto.getCompanyId(), personInfoVo.getMemberId());
      if (CollectionUtil.isNotEmpty(companyUserInfoList)){
        BeanUtil.copyProperties(companyUserInfoList.get(0), personInfoVo);
      }
      Member member = memberMapper.selectById(personInfoVo.getMemberId());
      personInfoVo.setRegMobile(member.getRegMobile());
    }
    return Result.success(page);
  }

}
