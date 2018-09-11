package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.ProviderCustom;
import com.ljwm.gecko.base.enums.ProviderCustomStatusEnum;
import com.ljwm.gecko.base.mapper.ProviderCustomMapper;
import com.ljwm.gecko.base.model.dto.ProviderCustomDto;
import com.ljwm.gecko.base.model.dto.ProviderCustomQueryDto;
import com.ljwm.gecko.base.model.vo.ProviderCustomVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProviderCustomService {

  @Autowired
  private ProviderCustomMapper providerCustomMapper;

  @Autowired
  private CommonService commonService;

  @Transactional
  public void save(ProviderCustomDto providerCustomDto){
    ProviderCustom providerCustom = new ProviderCustom();
    BeanUtil.copyProperties(providerCustomDto,providerCustom);
    if (providerCustom.getId()!=null){
      providerCustom.setUpdateTime(DateUtil.date());
      providerCustomMapper.updateById(providerCustom);
    }else {
      providerCustom.setCreateTime(DateUtil.date());
      providerCustom.setUpdateTime(DateUtil.date());
      providerCustom.setStatus(ProviderCustomStatusEnum.ENABLED.getCode());
      providerCustomMapper.insert(providerCustom);
    }
  }

  public Page<ProviderCustomVo> findByPage(ProviderCustomQueryDto providerCustomQueryDto){
    return commonService.find(providerCustomQueryDto, (p, q) -> providerCustomMapper.findByPage(p, Kv.by("text", providerCustomQueryDto.getText()).set("serviceId",providerCustomQueryDto.getServiceId()).set("providerId",providerCustomQueryDto.getProviderId())));
  }
}
