package com.ljwm.gecko.client.service;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.mapper.AttributeMapper;
import com.ljwm.gecko.base.model.vo.AttributeCompanyVo;
import com.ljwm.gecko.client.model.dto.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/20 18:14
 */
@Service
public class AttributeService {

  @Autowired
  AttributeMapper attributeMapper;

  public Result getAllAttribute(CompanyDto companyDto) {

    List<AttributeCompanyVo> list = attributeMapper.selectAllAttribute(companyDto.getCompanyId());
    return Result.success(list);
  }
}
