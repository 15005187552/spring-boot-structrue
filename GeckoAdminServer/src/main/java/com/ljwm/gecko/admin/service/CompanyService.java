package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.CompanyQuery;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.model.dto.AdminCompanyDto;
import com.ljwm.gecko.base.model.vo.UnValidateCompanyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class CompanyService {

  @Autowired
  private CompanyMapper companyMapper;

  @Autowired
  private CommonService commonService;

  public Page<UnValidateCompanyVo> findUnValidate(CompanyQuery query) {
//    Page page = new Page(query.getPage().getCurrent(),query.getPage().getSize());
//    page.setRecords(companyMapper.find(page, BeanUtil.beanToMap(query))) ;
//    return page;
    return commonService.find(query, (p, q) -> companyMapper.find(p, BeanUtil.beanToMap(query)));
  }

 public Page<AdminCompanyDto> find(CompanyQuery query) {
    return commonService.find(query, (p, q) -> companyMapper.find(p, BeanUtil.beanToMap(query)));
 }

}
