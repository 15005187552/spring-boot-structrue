package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.Order;
import com.ljwm.gecko.base.entity.OrderItem;
import com.ljwm.gecko.base.enums.OrderStatusEnum;
import com.ljwm.gecko.base.enums.PaymentTypeEnum;
import com.ljwm.gecko.base.mapper.OrderItemMapper;
import com.ljwm.gecko.base.mapper.OrderMapper;
import com.ljwm.gecko.base.model.dto.OrderDto;
import com.ljwm.gecko.base.model.dto.OrderItemDto;
import com.ljwm.gecko.base.model.vo.OrderSimpleVo;
import com.ljwm.gecko.base.model.vo.OrderVo;
import com.ljwm.gecko.base.utils.IdWorkerUtil;
import com.ljwm.gecko.base.utils.MoneyKit;
import com.ljwm.gecko.base.utils.UtilKit;
import com.ljwm.gecko.client.model.vo.OrderPaymentVo;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.webservice.WeiXinXcxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.ljwm.gecko.client.annotation.*;

@Slf4j
@Service
public class ClientOrderService {
  @Autowired
  private IdWorkerUtil idWorkerUtil;

  @Autowired
  private OrderItemMapper orderItemMapper;

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private CommonService commonService;

  @Autowired
  private WeiXinXcxService weiXinXcxService;

  private static final String MAIN_ORDER="MN";

  private static final String SUB_ORDER="SUB";

  @OrderLogger
  @Transactional
  public synchronized OrderVo placeOrder(OrderDto orderDto){
    OrderItemDto orderItemDto = orderDto.getOrderItemDto();
    if (orderItemDto!=null){
      OrderItem orderItem = new OrderItem();
      BeanUtil.copyProperties(orderItemDto,orderItem);
      orderItem.setOrderItemNo(SUB_ORDER+idWorkerUtil.nextId());
      orderItem.setCreateTime(DateUtil.date());
      orderItem.setUpdateTime(DateUtil.date());
      Order order = new Order();
      if (orderItemDto.getProductServiceId()!=null){
        //立即支付
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
      return new OrderVo(order);
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
      return new OrderVo(order);
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

  public OrderSimpleVo setOrderPaid(Long id){
    Order order = orderMapper.selectById(id);
    if (order==null){
      log.info("订单id{},查询不到该订单!",id);
      throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此订单!");
    }
    if (!Objects.equals(order.getStatus(),OrderStatusEnum.NO_PAID.getCode())){
      log.info("订单号{},非待付款状态!",order.getOrderNo());
      throw new LogicException(ResultEnum.DATA_ERROR,"订单为非待付款状态1");
    }
    order.setStatus(OrderStatusEnum.PAID.getCode());
    order.setPaymentTime(DateUtil.date());
    orderMapper.updateById(order);
    return new OrderSimpleVo(order);
  }

  public OrderPaymentVo payOrderXcx(Long id){
    Order order = orderMapper.selectById(id);
    if (order==null){
      log.info("订单id{},查询不到该订单!",id);
      throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此订单!");
    }

    // 2. 生成推送微信单号
    String wxNum = UtilKit.createNum("WX");
    order.setWxOrderNo(wxNum);
    orderMapper.updateById(order);
    JwtUser jwtUser = SecurityKit.currentUser();
    assert jwtUser != null;
    // 3. 下单
    Map map = weiXinXcxService.weixinPay(
      UtilKit.currentIp(),                    // ip
      wxNum,                                  // 微信单号
      MoneyKit.getFen(order.getPayment()),    // 金额
      jwtUser.getMember().getAccount().getAccount(),      // OPEN ID
      orderMapper.getOrderInfo(order.getOrderNo()),// 构造商品明细
      true,
      false
    );
    // 4. 构造返回值
    return new OrderPaymentVo(id, map);
  }
}
