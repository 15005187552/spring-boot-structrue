package com.ljwm.gecko.admin.service;

import com.ljwm.gecko.base.mapper.ProtocolMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: xixil
 * Date: 2018/10/19 10:05
 * RUA
 */

@Slf4j
@Service
public class ProtocolService {

  @Autowired
  private ProtocolMapper protocolMapper;
}
