package com.ljwm.gecko.provider.service;

import com.ljwm.gecko.base.mapper.SpecMapper;
import com.ljwm.gecko.base.model.vo.SpecVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProviderSpecService {

  @Autowired
  private SpecMapper specMapper;

  public List<SpecVo> findByServiceId(Integer serviceId){
    return specMapper.findByServiceId(serviceId);
  }
}
