package com.ljwm.gecko.provider.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.OrderItem;
import com.ljwm.gecko.base.enums.OrderStatusEnum;
import com.ljwm.gecko.base.mapper.OrderItemMapper;
import com.ljwm.gecko.base.mapper.OrderMapper;
import com.ljwm.gecko.base.model.dto.OrderItemQueryDto;
import com.ljwm.gecko.base.model.dto.OrderQueryDto;
import com.ljwm.gecko.base.model.vo.OrderItemVo;
import com.ljwm.gecko.base.model.vo.OrderVo;
import com.ljwm.gecko.provider.model.form.OrderItemPriceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@Slf4j
public class OrderProviderService {

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private OrderItemMapper orderItemMapper;

  @Autowired
  private CommonService commonService;

  public Page<OrderVo> find(OrderQueryDto orderQueryDto){
    return commonService.find(orderQueryDto, (p, q) -> orderMapper.findPage(p, Kv.by("text", orderQueryDto.getText()).set("status",orderQueryDto.getStatus()).set("providerId",orderQueryDto.getProviderId())));
  }

  public Page<OrderItemVo> findOrderItemList(OrderItemQueryDto orderItemQueryDto){
    return commonService.find(orderItemQueryDto, (p, q) -> orderItemMapper.findOrderItemList(p, BeanUtil.beanToMap(orderItemQueryDto)));
  }

  @Transactional
  public void resetPrice(OrderItemPriceDto orderItemPriceDto){
    OrderItem orderItem = orderItemMapper.selectById(orderItemPriceDto.getId());
    if (orderItem==null){
      log.info("根据id{},查询不到此订单明细信息!",orderItemPriceDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此订单明细信息!");
    }
    if (!Objects.equals(orderItem.getOrderItemStatus(),OrderStatusEnum.WAIT.getCode())){
      log.info("非待定价状态!");
      throw new LogicException(ResultEnum.DATA_ERROR,"非待定价状态!");
    }
    orderItem.setCurrentUnitPrice(orderItemPriceDto.getPrice());
    orderItem.setTotalPrice(orderItemPriceDto.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
    orderItem.setDownPaymentAmount(orderItem.getTotalPrice().multiply(orderItem.getDownPaymentRate()));
    orderItem.setRemainAmount(orderItem.getTotalPrice().subtract(orderItem.getDownPaymentAmount()));
    orderItem.setProviderContent(orderItemPriceDto.getProviderContent());
    orderItemMapper.updateById(orderItem);
  }
}
