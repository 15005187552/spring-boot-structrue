package com.ljwm.gecko.client.service;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.CityItemMapper;
import com.ljwm.gecko.base.model.vo.SpecialPercentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/13 14:32
 */
@Service
public class SpecialService {

  @Autowired
  CityItemMapper cityItemMapper;

  public Result getSpecial(String code) {
    Integer disableCode = DisabledEnum.ENABLED.getCode();
    List<SpecialPercentVo> list = cityItemMapper.getSpecial(code, disableCode);
    if(CollectionUtil.isNotEmpty(list)){
      return Result.success(list);
    }
    return Result.success(null);
  }
}
