package com.ljwm.gecko.im.service;

import com.ljwm.gecko.base.mapper.CustomerMessageMapper;
import com.ljwm.gecko.base.mapper.CustomerSessionMapper;
import com.ljwm.gecko.base.mapper.PushMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: xixil
 * Date: 2018/9/30 13:44
 * RUA
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class CustomerService {

  @Autowired
  private PushMessageMapper pushMessageMapper;

  @Autowired
  private CustomerMessageMapper customerMessageMapper;

  @Autowired
  private CustomerSessionMapper customerSessionMapper;

}
