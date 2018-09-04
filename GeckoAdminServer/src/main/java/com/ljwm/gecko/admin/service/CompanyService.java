package com.ljwm.gecko.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.CompanyQuery;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.model.vo.UnValidateConpanyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@SuppressWarnings("all")
public class CompanyService {

  @Autowired
  private CompanyMapper companyMapper;

  @Autowired
  private CommonService commonService;

  public Page<UnValidateConpanyVo> findUnValidate(CompanyQuery query){
    return null;
  }
}
