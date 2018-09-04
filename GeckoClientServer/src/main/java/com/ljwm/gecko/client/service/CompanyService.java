package com.ljwm.gecko.client.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.IdentificationType;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.model.ApplicationInfo;
import com.ljwm.gecko.client.model.dto.CompanyForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
      IdentificationType.NO_IDENTI.getCode(), DisabledEnum.ENABLED.getCode(), companyForm.getCode(), 1L,
    null, null, null, new Date(), null, companyForm.getProvCode(), companyForm.getCityCode(), companyForm.getAreaCode(), companyForm.getAddress());
    Map<String, Object> map = new HashMap<>();
    map.put("CODE", companyForm.getCityCode());
    map.put("DISABLED", DisabledEnum.ENABLED.getCode());
    List<Company> list = companyMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      company.setId(list.get(0).getId());
      companyMapper.updateById(company);
    } else {
      companyMapper.insert(company);
    }
    if(StrUtil.isNotBlank(companyForm.getFilePath())) {
      Fileutil.cutGeneralFile(appInfo.getCachePath() + companyForm.getFilePath(), appInfo.getCompanyFile() + company.getId());
      company.setPicPath(company.getId() + "/" + companyForm.getFilePath());
      companyMapper.updateById(company);
    }
    return Result.success("成功！");
  }


}
