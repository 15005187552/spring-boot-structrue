package com.ljwm.gecko.client.service;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.enums.TableNameEnum;
import com.ljwm.gecko.base.mapper.AttributeMapper;
import com.ljwm.gecko.base.model.vo.AttributeAttendanceVo;
import com.ljwm.gecko.base.model.vo.AttributeEmployVo;
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

    List<AttributeEmployVo> list = attributeMapper.selectAllAttribute(companyDto.getCompanyId(), TableNameEnum.T_ADD_SPECIAL.getCode());
    return Result.success(list);
  }

  public Result getAttendanceAttribute(CompanyDto companyDto) {
    List<AttributeAttendanceVo> list = attributeMapper.selectAttendanceAttribute(companyDto.getCompanyId());
    return Result.success(list);

  }
}
