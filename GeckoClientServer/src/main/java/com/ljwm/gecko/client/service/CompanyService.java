package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.IdentificationType;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.model.dto.CompanyForm;
import com.ljwm.gecko.client.model.vo.CompanyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public Result commit(CompanyForm companyForm) {
    Company company = new Company(null, companyForm.getName(), companyForm.getType(), new Date(),
      IdentificationType.NO_IDENTI.getCode(), DisabledEnum.ENABLED.getCode(), companyForm.getPhoneNum(), companyForm.getCode(), SecurityKit.currentId(),
    null, null, null, new Date(), null, companyForm.getProvCode(), companyForm.getCityCode(), companyForm.getAreaCode(), companyForm.getAddress());
    Map<String, Object> map = new HashMap<>();
    map.put("CODE", companyForm.getCode());
    map.put("DISABLED", DisabledEnum.ENABLED.getCode());
    List<Company> list = companyMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      company.setId(list.get(0).getId());
      companyMapper.updateById(company);
    } else {
      companyMapper.insert(company);
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
}
