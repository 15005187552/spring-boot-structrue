package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.Order;
import com.ljwm.gecko.base.entity.OrderItem;
import com.ljwm.gecko.base.enums.OrderStatusEnum;
import com.ljwm.gecko.base.enums.PaymentTypeEnum;
import com.ljwm.gecko.base.mapper.OrderItemMapper;
import com.ljwm.gecko.base.mapper.OrderMapper;
import com.ljwm.gecko.base.model.dto.OrderDto;
import com.ljwm.gecko.base.model.dto.OrderItemDto;
import com.ljwm.gecko.base.utils.IdWorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OrderService {

  @Autowired
  private IdWorkerUtil idWorkerUtil;

  @Autowired
  private OrderItemMapper orderItemMapper;

  @Autowired
  private OrderMapper orderMapper;

  private static final String MAIN_ORDER="MN";

  private static final String SUB_ORDER="SUB";

  @Transactional
  public void  createOrder(OrderDto orderDto){

    OrderItemDto orderItemDto = orderDto.getOrderItemDto();
    if (orderItemDto!=null){
      OrderItem orderItem = new OrderItem();
      BeanUtil.copyProperties(orderItemDto,orderItem);
      orderItem.setOrderItemNo(SUB_ORDER+idWorkerUtil.nextId());
      orderItem.setCreateTime(DateUtil.date());
      orderItem.setUpdateTime(DateUtil.date());
      if (orderItemDto.getProductServiceId()!=null){
        //立即支付
        Order order = new Order();
        order.setMemberId(SecurityKit.currentId());
        order.setOrderNo(MAIN_ORDER+idWorkerUtil.nextId());
        order.setPaymentType(PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setCreateTime(DateUtil.date());
        order.setPayment(orderItemDto.getCurrentUnitPrice());  //暂时不考虑数量
        order.setStatus(OrderStatusEnum.NO_PAID.getCode());
        orderMapper.insert(order);
        orderItem.setOrderNo(order.getOrderNo());
        orderItem.setOrderItemStatus(OrderStatusEnum.NO_PAID.getCode());

      }else {
        orderItem.setOrderItemStatus(OrderStatusEnum.WAIT.getCode());
      }
      orderItemMapper.insert(orderItem);
    }else {
      //多服务支付
      List<String> orderItemOrderList = orderDto.getOrderItemNoList();
      if (CollectionUtils.isEmpty(orderItemOrderList)){
        log.info("提交订单,订单明细不能为空");
        throw new LogicException(ResultEnum.DATA_ERROR,"服务明细不能为空!");
      }
      List<OrderItem> orderItemList = Lists.newArrayList();
      BigDecimal totalPrice = BigDecimal.ZERO;
      for (String orderItemNo:orderItemOrderList){
        OrderItem orderItem = orderItemMapper.findByOrderItemNo(orderItemNo);
        if (orderItem==null|| !Objects.equals(orderItem.getOrderItemStatus(),OrderStatusEnum.NO_PAID.getCode())){
          log.info("订单号{},为非未支付状态,不能下单");
          throw new LogicException(ResultEnum.DATA_ERROR,"订单非支付状态,不能支付");
        }
        orderItemList.add(orderItem);
        totalPrice = totalPrice.add(new BigDecimal(orderItem.getCurrentUnitPrice().doubleValue())) ;
      }
      Order order = new Order();
      order.setMemberId(SecurityKit.currentId());
      order.setOrderNo(MAIN_ORDER+idWorkerUtil.nextId());
      order.setPaymentType(PaymentTypeEnum.ONLINE_PAY.getCode());
      order.setCreateTime(DateUtil.date());
      order.setPayment(totalPrice);  //暂时不考虑数量
      order.setStatus(OrderStatusEnum.NO_PAID.getCode());
      orderMapper.insert(order);
      for (OrderItem orderItem : orderItemList){
        orderItem.setOrderNo(order.getOrderNo());
        orderItem.setUpdateTime(DateUtil.date());
        orderItemMapper.updateById(orderItem);
      }
    }
  }

  @Transactional
  public void createOrderItem(OrderItemDto orderItemDto){
    OrderItem orderItem = new OrderItem();
    BeanUtil.copyProperties(orderItemDto,orderItem);
    orderItem.setOrderItemNo(SUB_ORDER+idWorkerUtil.nextId());
    orderItem.setCreateTime(DateUtil.date());
    orderItem.setUpdateTime(DateUtil.date());
    if (orderItem.getProductServiceId()!=null){
      orderItem.setOrderItemStatus(OrderStatusEnum.NO_PAID.getCode());
    }else {
      orderItem.setOrderItemStatus(OrderStatusEnum.WAIT.getCode());
    }
    orderItemMapper.insert(orderItem);
  }
}