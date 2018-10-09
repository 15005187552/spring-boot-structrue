package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.enums.OrderStatusEnum;
import com.ljwm.gecko.base.enums.PaymentTypeEnum;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.model.vo.OrderItemVo;
import com.ljwm.gecko.base.model.vo.OrderSimpleVo;
import com.ljwm.gecko.base.model.vo.OrderVo;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.base.utils.IdWorkerUtil;
import com.ljwm.gecko.base.utils.MoneyKit;
import com.ljwm.gecko.base.utils.UtilKit;
import com.ljwm.gecko.client.model.vo.OrderPaymentVo;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.webservice.WeiXinXcxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
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

  @Autowired
  private OrderCommentsMapper orderCommentsMapper;

  @Autowired
  private OrderCommentsPathMapper orderCommentsPathMapper;

  @Autowired
  private SpecServicesPriceMapper specServicesPriceMapper;

  @Autowired
  private AppInfo appInfo;

  private static final String MAIN_ORDER="MN";

  private static final String SUB_ORDER="SUB";

  @OrderLogger
  @Transactional
  public synchronized OrderVo placeOrder(OrderDto orderDto){
    List<Long> orderItemOrderList = orderDto.getOrderItemNoList();
    if (CollectionUtils.isEmpty(orderItemOrderList)){
      log.info("提交订单,订单明细不能为空");
      throw new LogicException(ResultEnum.DATA_ERROR,"服务明细不能为空!");
    }
    Map<String,Object> resMap = calMoney(orderItemOrderList);
    Order order = new Order();
    order.setMemberId(SecurityKit.currentId());
    order.setOrderNo(MAIN_ORDER+idWorkerUtil.nextId());
    order.setPaymentType(PaymentTypeEnum.ONLINE_PAY.getCode());
    order.setCreateTime(DateUtil.date());
    order.setPayment((BigDecimal)(resMap.get("totalAmount")));  //暂时不考虑数量
    order.setStatus(OrderStatusEnum.NO_PAID.getCode());
    order.setDownPaymentAmount((BigDecimal)(resMap.get("downPaymentAmount")));
    order.setRemianAmount((BigDecimal)(resMap.get("remainAmount")));
    orderMapper.insert(order);
    List<OrderItem> orderItemList = (List<OrderItem>)resMap.get("orderItemsList");
    for (OrderItem orderItem : orderItemList){
      orderItem.setOrderNo(order.getOrderNo());
      orderItem.setUpdateTime(DateUtil.date());
      orderItemMapper.updateById(orderItem);
    }
    return new OrderVo(order);
  }

  public Map<String,Object> calMoney(List<Long> orderItemOrderList){
    Map<String,Object> retMap = Maps.newHashMap();
    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal downPaymentAmount = BigDecimal.ZERO;
    BigDecimal remainAmount = BigDecimal.ZERO;
    List<OrderItem> orderItemList = Lists.newArrayList();
    for (Long orderItemId:orderItemOrderList){
      OrderItem orderItem = orderItemMapper.selectById(orderItemId);
      if (orderItem==null|| !Objects.equals(orderItem.getOrderItemStatus(),OrderStatusEnum.NO_PAID.getCode())){
        log.info("订单号{},为非未支付状态,不能下单");
        throw new LogicException(ResultEnum.DATA_ERROR,"订单非未支付状态,不能支付");
      }
      if (StringUtils.isNotEmpty(orderItem.getOrderNo())){
        log.info("订单号{},已创建支付订单,不可重复创建!");
        throw new LogicException(ResultEnum.DATA_ERROR,"已创建支付订单,不可重复创建!");
      }
      orderItemList.add(orderItem);
      totalAmount = totalAmount.add(new BigDecimal(orderItem.getTotalPrice().doubleValue())) ;
      downPaymentAmount = downPaymentAmount.add(new BigDecimal(orderItem.getDownPaymentAmount().doubleValue()));
      remainAmount = remainAmount.add(new BigDecimal(orderItem.getRemainAmount().doubleValue()));
    }
    retMap.put("totalAmount",totalAmount);
    retMap.put("downPaymentAmount",downPaymentAmount);
    retMap.put("remainAmount",remainAmount);
    retMap.put("orderItemsList",orderItemList);
    return retMap;
  }

  @Transactional
  public void createOrderItem(OrderItemDto orderItemDto){
    OrderItem orderItem = new OrderItem();
    BeanUtil.copyProperties(orderItemDto,orderItem);
    orderItem.setOrderItemNo(SUB_ORDER+idWorkerUtil.nextId());
    orderItem.setCreateTime(DateUtil.date());
    orderItem.setUpdateTime(DateUtil.date());
    if (orderItemDto.getSpecServiceId()!=null){
      orderItem.setOrderItemStatus(OrderStatusEnum.NO_PAID.getCode());
      SpecServicesPrice specServicesPrice =specServicesPriceMapper.selectById(orderItemDto.getSpecServiceId());
      if (specServicesPrice==null){
        log.info("根据商品规格id{}查询商品规格信息不存在!");
        throw new LogicException(ResultEnum.DATA_ERROR,"查询商品规格信息不存在!");
      }
      orderItem.setCurrentUnitPrice(specServicesPrice.getPrice());
      orderItem.setDownPaymentRate(specServicesPrice.getDownPaymentRate());
      orderItem.setTotalPrice(specServicesPrice.getPrice().multiply(new BigDecimal(orderItemDto.getQuantity())));
      orderItem.setDownPaymentAmount(orderItem.getTotalPrice().multiply(specServicesPrice.getDownPaymentRate()));
      orderItem.setRemainAmount(orderItem.getTotalPrice().subtract(orderItem.getDownPaymentAmount()));
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

  public OrderSimpleVo comments(OrderCommentsDto orderCommentsDto){
    Order order = orderMapper.selectById(orderCommentsDto.getId());
    if (order==null){
      log.info("订单id{},查询不到该订单!",orderCommentsDto.getId());
      throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此订单!");
    }
    if (!Objects.equals(order.getStatus(),OrderStatusEnum.PAID.getCode())){
      log.info("订单号{},非已付款状态!",order.getOrderNo());
      throw new LogicException(ResultEnum.DATA_ERROR,"订单为非已付款状态!");
    }
    order.setStatus(OrderStatusEnum.ORDER_SUCCESS.getCode());
    order.setEndTime(DateUtil.date());
    orderMapper.updateById(order);
    List<OrderItemCommentsDto> commentsList = orderCommentsDto.getComments();
    if (CollectionUtils.isEmpty(commentsList)){
      log.info("评价列表不能为空!");
      throw new LogicException(ResultEnum.DATA_ERROR,"评价列表不能为空!");
    }
    for (OrderItemCommentsDto orderItemCommentsDto: commentsList){
      OrderComments orderComments = new OrderComments();
      orderComments.setMemberId(SecurityKit.currentId());
      orderComments.setOrderId(orderCommentsDto.getId());
      orderComments.setOrderItemId(orderItemCommentsDto.getId());
      orderComments.setName(SecurityKit.currentUser().getUsername());
      orderComments.setStar(orderItemCommentsDto.getStar());
      orderComments.setCreateTime(DateUtil.date());
      orderCommentsMapper.insert(orderComments);
      if (CollectionUtils.isNotEmpty(orderItemCommentsDto.getPictures())){
        for (String picPath:orderItemCommentsDto.getPictures() ){
          OrderCommentsPath orderCommentsPath = new OrderCommentsPath();
          String srcPath = appInfo.getFilePath() + Constant.CACHE + picPath;
          String destDir = appInfo.getFilePath() + Constant.MEMBER + SecurityKit.currentId() + "/";
          Fileutil.cutGeneralFile(srcPath, destDir);
          orderCommentsPath.setPicPath(Constant.MEMBER + SecurityKit.currentId() + "/" + picPath);
          orderCommentsPath.setCommentId(orderComments.getId());
          orderCommentsPath.setCreateTime(DateUtil.date());
          orderCommentsPathMapper.insert(orderCommentsPath);
        }

      }
    }
    return new OrderSimpleVo(order);
  }

  public Page<OrderItemVo> findOrderItemList(OrderItemQueryDto orderItemQueryDto){
    return commonService.find(orderItemQueryDto, (p, q) -> orderItemMapper.findOrderItemList(p, BeanUtil.beanToMap(orderItemQueryDto)));
  }

  public Page<OrderVo> findOrderList(OrderQueryDto orderQueryDto){
    return commonService.find(orderQueryDto, (p, q) -> orderMapper.findPage(p, BeanUtil.beanToMap(orderQueryDto)));
  }
}
