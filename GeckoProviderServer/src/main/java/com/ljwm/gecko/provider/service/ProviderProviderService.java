package com.ljwm.gecko.provider.service;

import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.base.entity.Provider;
import com.ljwm.gecko.base.entity.ProviderServices;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.ProviderMapper;
import com.ljwm.gecko.base.mapper.ProviderServicesMapper;
import com.ljwm.gecko.base.mapper.SpecServicesPriceMapper;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.provider.model.form.ProviderDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProviderProviderService {

  @Autowired
  private ProviderMapper providerMapper;

  @Autowired
  private ProviderServicesMapper providerServicesMapper;

  @Autowired
  private SpecServicesPriceMapper specServicesPriceMapper;

  public List<ProviderVo> findProviderListByMemberId(Long memberId){
    return providerMapper.findProviderListByMemberId(memberId);
  }

  public ProviderVo findProviderByProviderId(Long providerId){
    return providerMapper.findProviderByProviderId(providerId);
  }

  @Transactional
  public Boolean disabled(Long id,Integer status){
    //启用
    if (Objects.equals(DisabledEnum.ENABLED.getCode(),status)){
      return providerServicesMapper.disabled(id,status)>=1;
    }else if (Objects.equals(DisabledEnum.DISABLED.getCode(),status)){
      //禁用
      providerServicesMapper.disabled(id,status);
      ProviderServices providerServices = providerServicesMapper.selectById(id);
      if (providerServices==null){
        log.info("根据id{}，查询不到服务商服务信息!");
        throw new LogicException(ResultEnum.DATA_ERROR,"查询不到服务商服务信息!");
      }
      Long providerId = providerServices.getProviderId();
      Integer serviceId = providerServices.getServiceId();
      specServicesPriceMapper.disabled(serviceId,status,providerId);
      return true;
    }else {
      log.info("状态:{}非法,请核对后提交",status);
      throw new LogicException(ResultEnum.DATA_ERROR,"状态非法!");
    }
  }

  public void updateProviderDetail(ProviderDetailDto providerDetailDto) {
    Provider provider = providerMapper.selectById(providerDetailDto.getId());
    if (provider==null){
      log.info("根据服务商id:{},查询不到此服务商信息!");
      throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此服务商信息!");
    }
    provider.setDetail(providerDetailDto.getDetail());
    providerMapper.updateById(provider);
  }
}
