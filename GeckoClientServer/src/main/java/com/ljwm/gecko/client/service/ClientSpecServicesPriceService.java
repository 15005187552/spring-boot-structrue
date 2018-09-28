package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.mapper.SpecServicesPriceMapper;
import com.ljwm.gecko.base.model.vo.SpecServicesPriceSimpleVo;
import com.ljwm.gecko.client.model.dto.SpecServicePriceQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientSpecServicesPriceService {

  @Autowired
  private SpecServicesPriceMapper specServicesPriceMapper;


  public SpecServicesPriceSimpleVo findSpecServicesPrice(SpecServicePriceQueryForm specServicePriceQueryForm){
    return specServicesPriceMapper.findByMap(BeanUtil.beanToMap(specServicePriceQueryForm));
  }
}
