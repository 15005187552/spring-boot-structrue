package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.enums.*;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import com.ljwm.gecko.base.model.dto.SpecailForm;
import com.ljwm.gecko.base.model.vo.EmployeeVo;
import com.ljwm.gecko.base.service.LocationService;
import com.ljwm.gecko.base.utils.EnumUtil;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.model.dto.CompanyForm;
import com.ljwm.gecko.client.model.vo.CompanyVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  @Transactional
  public Result commit(CompanyForm companyForm) {

    Company company = new Company();
    BeanUtils.copyProperties(companyForm, company);
    Date date = new Date();
    company.setValidateState(IdentificationType.NO_IDENTI.getCode())
      .setUpdateTime(date).setCreaterId(SecurityKit.currentId())
      .setDisabled(DisabledEnum.ENABLED.getCode());
    Map<String, Object> map = new HashMap<>();
    map.put("CODE", companyForm.getCode());
    map.put("DISABLED", DisabledEnum.ENABLED.getCode());
    List<Company> list = companyMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      company.setId(list.get(0).getId());
      companyMapper.updateById(company);
    } else {
      company.setCreateTime(date);
      companyMapper.insert(company);
      int codeLenth = RoleCodeType.values().length;
      String[] code = new String[codeLenth];
      code[codeLenth-RoleCodeType.ADMIN.getDigit()] = String.valueOf(RoleCodeType.ADMIN.getValue());
      code[codeLenth-RoleCodeType.CREATOR.getDigit()] = String.valueOf(RoleCodeType.CREATOR.getValue());
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
    if(StrUtil.isNotBlank(companyForm.getFilePath())&&companyForm.getFilePath().indexOf(Constant.HTTP) == -1) {
      String srcPath = appInfo.getFilePath() + Constant.CACHE + companyForm.getFilePath();
      String destDir = appInfo.getFilePath()+Constant.COMPANY + company.getId()+ "/";
      Fileutil.cutGeneralFile(srcPath, destDir);
      company.setPicPath(Constant.COMPANY + company.getId() + "/" + companyForm.getFilePath());
    }
    companyMapper.updateById(company);
    return Result.success("成功！");
  }

  public Result findByName(String name) {
    Map<String, Object> map = new HashMap<>();
    map.put("NAME", name);
    map.put("VALIDATE_STATE", IdentificationType.PASS_IDENTI.getCode());
    List<Company> list = companyMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      Company company = list.get(0);
      CompanyVo companyVo = new CompanyVo();
      BeanUtil.copyProperties(company, companyVo);
      if(StrUtil.isNotBlank(company.getPicPath())) {
        companyVo.setFilePath(appInfo.getWebPath() + company.getPicPath());
      }
      return Result.success(companyVo);
    }
    return Result.success(null);
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

  public Result findCompanyById(String companyId) {
    Map<String, Object> map = new HashedMap();
    map.put("ID", companyId);
    List<Company> list = companyMapper.selectByMap(map);
    if (CollectionUtil.isNotEmpty(list)) {
      Company company = list.get(0);
      CompanyVo companyVo = new CompanyVo();
      BeanUtil.copyProperties(company, companyVo);
      return Result.success(companyVo);
    }
    return Result.success("该公司不存在！");
  }
}
