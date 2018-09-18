package com.ljwm.gecko.provider.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.mapper.OrderMapper;
import com.ljwm.gecko.base.model.dto.OrderQueryDto;
import com.ljwm.gecko.base.model.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProviderService {

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private CommonService commonService;

  public Page<OrderVo> find(OrderQueryDto orderQueryDto){
    orderQueryDto.setProviderId(2l);
    return commonService.find(orderQueryDto, (p, q) -> orderMapper.findPage(p, Kv.by("text", orderQueryDto.getText()).set("status",orderQueryDto.getStatus()).set("providerId",orderQueryDto.getProviderId())));
  }
}
