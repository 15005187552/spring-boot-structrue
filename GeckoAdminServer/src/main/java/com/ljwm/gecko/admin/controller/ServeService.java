package com.ljwm.gecko.admin.controller;

import com.ljwm.gecko.base.mapper.ServiceMapper;
import com.ljwm.gecko.base.model.vo.ServiceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ServeService {

  @Autowired
  private ServiceMapper serviceMapper;

  public List<ServiceVo> find(){
    return serviceMapper.find();
  }
}
