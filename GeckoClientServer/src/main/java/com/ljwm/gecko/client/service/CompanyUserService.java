package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.enums.ActivateEnum;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import com.ljwm.gecko.base.mapper.NaturalPersonMapper;
import com.ljwm.gecko.base.model.dto.MemberComForm;
import com.ljwm.gecko.base.model.vo.CompanyVo;
import com.ljwm.gecko.client.dao.CompanyUserDao;
import com.ljwm.gecko.client.model.dto.InactiveForm;
import com.ljwm.gecko.client.model.dto.MemberForm;
import com.ljwm.gecko.client.model.vo.CompanyInfoVo;
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
        if (companyId.equals(companyInfoVo.getId())){
          companyInfoVo.setFlag(true);
        }
        companyVoList.add(companyInfoVo);
        return Result.success(companyVoList);
      }
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
