package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.CityItem;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.entity.CompanySpecial;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.enums.*;
import com.ljwm.gecko.base.mapper.CityItemMapper;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.mapper.CompanySpecialMapper;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import com.ljwm.gecko.base.model.dto.SpecailForm;
import com.ljwm.gecko.base.model.vo.CompanyVo;
import com.ljwm.gecko.base.model.vo.EmployeeVo;
import com.ljwm.gecko.base.service.LocationService;
import com.ljwm.gecko.base.utils.EnumUtil;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.model.dto.CompanyForm;
import com.ljwm.gecko.client.model.dto.CompanySpecialForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/3 10:55
 */
@Service
@Slf4j
public class CompanyService {

  @Autowired
  ApplicationInfo appInfo;

  @Autowired
  CompanyMapper companyMapper;

  @Autowired
  LocationService locationService;

  @Autowired
  CompanyUserMapper companyUserMapper;

  @Autowired
  CityItemMapper cityItemMapper;

  @Autowired
  CompanySpecialMapper companySpecialMapper;

  @Transactional
  public Result commit(CompanyForm companyForm) {
    Company company = new Company();
    BeanUtils.copyProperties(companyForm, company);
    Date date = new Date();
    company.setValidateState(IdentificationType.NO_IDENTI.getCode())
      .setUpdateTime(date).setDisabled(DisabledEnum.ENABLED.getCode());
    if(companyForm.getCompanyId() != null){
      //更新操作
      companyMapper.updateById(company);
      List<CompanySpecialForm> list = companyForm.getCompanySpecialList();
      for (CompanySpecialForm companySpecialForm : list){
        CompanySpecial companySpecial = new CompanySpecial();
        BeanUtil.copyProperties(companySpecialForm, companySpecial);
        companySpecial.setCompanyId(companyForm.getCompanyId());
        if (companySpecial.getId() != null){
          companySpecialMapper.updateById(companySpecial);
        }else {
          companySpecialMapper.insert(companySpecial);
        }
      }
    } else {
      company = companyMapper.selectOne(new QueryWrapper<Company>().eq(Company.NAME, company.getName()).eq(Company.CODE, company.getCode()));
      if(company != null){
        company.setUpdateTime(new Date());
        companyMapper.updateById(company);
      }else {
        company.setCreateTime(date).setCreaterId(SecurityKit.currentId()).setUpdateTime(date).setValidateState(IdentificationType.NO_IDENTI.getCode())
          .setUpdateTime(date).setDisabled(DisabledEnum.ENABLED.getCode());;
        companyMapper.insert(company);
      }
      List<CityItem> list = cityItemMapper.selectList(new QueryWrapper<CityItem>().eq(CityItem.REGION_CODE, companyForm.getCityCode()));
      for (CityItem cityItem : list){
        CompanySpecial companySpecial = new CompanySpecial();
        companySpecial.setCompanyPer(cityItem.getCompanyPer()).setPersonPer(cityItem.getPersonPer())
          .setSpecialId(cityItem.getItemType()).setCompanyId(company.getId()).setCompanyId(company.getId());
        companySpecialMapper.insert(companySpecial);
      }
      //把创建人添加到t_company_user
      int codeLenth = RoleCodeType.values().length;
      String[] code = new String[codeLenth];
      for (RoleCodeType roleCodeType: RoleCodeType.values()){
        code[codeLenth-roleCodeType.getDigit()] = String.valueOf(roleCodeType.getValue());
      }
      code[codeLenth-RoleCodeType.ITIN.getDigit()] = "0";
      StringBuffer s = new StringBuffer();
      for(String str :code){
        s.append(str);
      }
      String roleCode = s.toString();
      CompanyUser companyUser = new CompanyUser(null, company.getId(), SecurityKit.currentId(), roleCode, new Date(), new Date(), DisabledEnum.ENABLED.getCode(), ActivateEnum.ENABLED.getCode());
      companyUserMapper.insert(companyUser);
      File file = new File(appInfo.getFilePath()+ Constant.COMPANY+ company.getId());
      if(!file.exists()){
        file.mkdirs();
      }
    }
    if(StrUtil.isNotBlank(companyForm.getFilePath())) {
      String srcPath = appInfo.getFilePath() + Constant.CACHE + companyForm.getFilePath();
      String destDir = appInfo.getFilePath()+Constant.COMPANY + company.getId()+ "/";
      Fileutil.cutGeneralFile(srcPath, destDir);
      company.setPicPath(Constant.COMPANY + company.getId() + "/" + companyForm.getFilePath());
    }
    companyMapper.updateById(company);
    CompanyVo companyVo = new CompanyVo();
    BeanUtil.copyProperties(company, companyVo);
    return Result.success(companyVo);
  }

  public CompanyVo findByName(String name) {
    List<CompanyVo> list = companyMapper.findCompanyByName(name, CompanyValidateEnum.PASS_VALIDATE.getCode());
    if (CollectionUtil.isEmpty(list)) {
      throw new LogicException("无搜索结果！");
    }
    return list.get(0);
  }

  public Result findEmployee(String companyId) {
    List<EmployeeVo> list = companyUserMapper.selectEmployee(companyId, DisabledEnum.ENABLED.getCode(), ActivateEnum.ENABLED.getCode());
    if (CollectionUtil.isNotEmpty(list)) {
      for (EmployeeVo employeeVo : list){
        employeeVo.setCertType(EnumUtil.getEnumBycode(CertificateType.class, employeeVo.getCertType()).getName());
      }
      return Result.success(list);
    }
    return Result.success(null);
  }

  @Transactional
  public Result postSpecial(SpecailForm specailForm) {
    Company company = companyMapper.selectById(specailForm.getCompanyId());
    if (company != null){
      BeanUtil.copyProperties(specailForm, company);
      company.setUpdateTime(new Date());
      companyMapper.updateById(company);
      return Result.success("成功！");
    }
    return Result.fail("请先提交公司信息！");
  }

  public CompanyVo findCompanyById(String companyId) {
    List<CompanyVo> list = companyMapper.findCompanyById(companyId);
    if (CollectionUtil.isEmpty(list)) {
      throw new LogicException("无搜索结果！");
    }
    return list.get(0);
  }
}
