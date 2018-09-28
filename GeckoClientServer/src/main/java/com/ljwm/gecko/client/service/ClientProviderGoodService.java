package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.ProviderGoodsMapper;
import com.ljwm.gecko.base.model.vo.ProviderGoodsVo;
import com.ljwm.gecko.client.model.dto.ProviderGoodQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientProviderGoodService {

  @Autowired
  private ProviderGoodsMapper providerGoodsMapper;

  @Autowired
  private CommonService commonService;

  public Page<ProviderGoodsVo> findGoodsListByServiceId(ProviderGoodQueryDto providerGoodQueryDto){
    providerGoodQueryDto.setStatus(DisabledEnum.ENABLED.getCode());
    return commonService.find(providerGoodQueryDto, (p, q) -> providerGoodsMapper.findByPage(p, BeanUtil.beanToMap(providerGoodQueryDto)));
  }
}
