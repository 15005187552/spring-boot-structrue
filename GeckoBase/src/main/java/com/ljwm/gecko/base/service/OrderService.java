package com.ljwm.gecko.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.mapper.OrderMapper;
import com.ljwm.gecko.base.model.dto.OrderQueryDto;
import com.ljwm.gecko.base.model.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private OrderMapper orderMapper;

  private Page<OrderVo> find(OrderQueryDto orderQueryDto){
    return commonService.find(orderQueryDto, (p, q) -> orderMapper.findPage(p, Kv.by("text", orderQueryDto.getText()).set("status",orderQueryDto.getStatus())));
  }
}
