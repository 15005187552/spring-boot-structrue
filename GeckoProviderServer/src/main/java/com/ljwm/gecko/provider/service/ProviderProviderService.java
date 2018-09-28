package com.ljwm.gecko.provider.service;

import com.ljwm.gecko.base.mapper.ProviderMapper;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProviderProviderService {

  @Autowired
  private ProviderMapper providerMapper;

  public List<ProviderVo> findProviderListByMemberId(Long memberId){
    return providerMapper.findProviderListByMemberId(memberId);
  }

  public ProviderVo findProviderByProviderId(Long providerId){
    return providerMapper.findProviderByProviderId(providerId);
  }
}
