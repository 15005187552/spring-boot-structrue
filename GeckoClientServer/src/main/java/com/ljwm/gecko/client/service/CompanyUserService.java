package com.ljwm.gecko.client.service;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import com.ljwm.gecko.base.model.dto.MemberComForm;
import com.ljwm.gecko.client.dao.CompanyUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public Result memberEnterCom(MemberComForm memberComForm) {
    return companyUserDao.insertOrUpdate(memberComForm.getMemberId(), memberComForm.getCompanyId(), memberComForm.getRoleCode());
  }

  public Result findCompany() {
    Long memberId = SecurityKit.currentId();
    return Result.success(companyUserMapper.findCompany(memberId));
  }
}
