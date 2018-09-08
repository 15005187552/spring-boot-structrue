package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.CompanyCheckForm;
import com.ljwm.gecko.admin.model.form.CompanyQuery;
import com.ljwm.gecko.admin.model.vo.SimpleCompany;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.model.dto.AdminCompanyDto;
import com.ljwm.gecko.base.model.vo.UnValidateCompanyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    return commonService.find(query, (p, q) -> companyMapper.find(p, BeanUtil.beanToMap(query)));
  }

  public Page<AdminCompanyDto> find(CompanyQuery query) {
    return commonService.find(query, (p, q) -> companyMapper.find(p, BeanUtil.beanToMap(query)));
  }


  public Company checkCompany(CompanyCheckForm form) {
    if (Objects.isNull(form.getId())) throw new LogicException(ResultEnum.DATA_ERROR, "企业ID不能未空");
    Company company = companyMapper.selectById(form.getId());
    if (Objects.isNull(company)) throw new LogicException(ResultEnum.DATA_ERROR, "未找到ID为" + form.getId() + "的企业");
    company.setValidateState(form.getIsPass()).setValidateTime(DateTime.now())
      .setValidatorId(SecurityKit.currentId()).setValidateText(form.getText());
    companyMapper.updateById(company);
    return company;
  }

  public List<SimpleCompany> getCompany() {
    return companyMapper.selectList(null).stream().map(SimpleCompany::new).collect(Collectors.toList());
  }

}
