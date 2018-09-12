package com.ljwm.gecko.client.service;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.enums.ActivateEnum;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import com.ljwm.gecko.base.mapper.NaturalPersonMapper;
import com.ljwm.gecko.base.model.dto.MemberComForm;
import com.ljwm.gecko.client.dao.CompanyUserDao;
import com.ljwm.gecko.client.model.dto.InactiveForm;
import com.ljwm.gecko.client.model.dto.MemberForm;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  NaturalPersonMapper naturalPersonMapper;

  public Result memberEnterCom(MemberComForm memberComForm) {
    companyUserDao.insertOrUpdate(memberComForm.getMemberId(), memberComForm.getCompanyId(), memberComForm.getRoleCode());
    return Result.success("成功！");
  }

  public Result findCompany() {
    Long memberId = SecurityKit.currentId();
    return Result.success(companyUserMapper.findCompany(memberId));
  }

  public Result inactiveCompany(MemberForm memberForm) {
    Map<String, Object> map = new HashedMap();
    map.put("MEMBER_ID", memberForm.getMemberId());
    map.put("ACTIATED", ActivateEnum.DISABLED);
    List<CompanyUser> list = companyUserMapper.selectByMap(map);
    return Result.success(list);
  }

  public Result enterCompany(InactiveForm inactiveForm) {
    Map<String, Object> map = new HashedMap();
    map.put("COMPANY_ID", inactiveForm.getCompanyId());
    map.put("MEMBER_ID", inactiveForm.getMemberId());
    List<CompanyUser> list = companyUserMapper.selectByMap(map);
    if(CollectionUtil.isEmpty(list)){
      return Result.fail("传入的参数有误!");
    }
    CompanyUser companyUser = list.get(0);
    if(inactiveForm.isFlag()){
      NaturalPerson naturalPerson = naturalPersonMapper.selectById(inactiveForm.getMemberId());
      if(naturalPerson != null) {
        naturalPerson.setCompanyId(inactiveForm.getCompanyId());
        naturalPersonMapper.updateById(naturalPerson);
      }
    }
    if(inactiveForm.getActivated() != null){
      companyUser.setActivated(inactiveForm.getActivated());
    }
    companyUserMapper.updateById(companyUser);
    return Result.success("成功！");
  }
}
