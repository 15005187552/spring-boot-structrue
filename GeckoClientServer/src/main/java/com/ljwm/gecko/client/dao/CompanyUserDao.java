package com.ljwm.gecko.client.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.enums.ActivateEnum;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/8 17:52
 */
@Repository
public class CompanyUserDao {

  @Autowired
  CompanyUserMapper companyUserMapper;

  public Result insertOrUpdate(Long memberId, Long companyId, Integer roleCode) {
    Map<String, Object> map = new HashedMap();
    map.put("MEMBER_ID", memberId);
    map.put("COMPANY_ID", companyId);
    List<CompanyUser> list = companyUserMapper.selectByMap(map);
    CompanyUser companyUser;
    if(CollectionUtil.isNotEmpty(list)){
      companyUser = list.get(0);
      companyUser.setRolesCode(roleCode);
      companyUserMapper.updateById(companyUser);
    } else {
      Date date = new Date();
      companyUser = new CompanyUser(null, companyId, memberId, roleCode, date, date, DisabledEnum.ENABLED.getCode(),null);
      if(memberId == SecurityKit.currentId()){
        companyUser.setActivated(ActivateEnum.ENABLED.getCode());
      } else {
        companyUser.setActivated(ActivateEnum.DISABLED.getCode());
      }
      companyUserMapper.insert(companyUser);
    }
    return null;
  }
}
