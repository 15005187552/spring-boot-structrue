package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.enums.DictEnum;
import com.ljwm.gecko.base.enums.OrderStatusEnum;
import com.ljwm.gecko.base.enums.PaymentTypeEnum;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.model.vo.OrderItemVo;
import com.ljwm.gecko.base.model.vo.OrderSimpleVo;
import com.ljwm.gecko.base.model.vo.OrderVo;
import com.ljwm.gecko.base.model.vo.SpecServicesPriceSimpleVo;
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
  private OrderPayInfoMapper orderPayInfoMapper;

  @Autowired
  private ServiceTypeMapper serviceTypeMapper;

  @Autowired
  private DictMapper dictMapper;

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
    OrderPayInfo orderPayInfo = new OrderPayInfo();
    orderPayInfo.setMemberId(SecurityKit.currentId());
    orderPayInfo.setOrderNo(order.getOrderNo());
    orderPayInfo.setStatus(0);//设置为未支付
    orderPayInfo.setType(0);//设置为首付款
    orderPayInfo.setPayAmount(order.getDownPaymentAmount());
    orderPayInfo.setUpdateTime(DateUtil.date());
    orderPayInfo.setCreateTime(DateUtil.date());
    orderPayInfoMapper.insert(orderPayInfo);
    return new OrderVo(order);
  }

  @OrderLogger
  @Transactional
  public synchronized OrderVo placeRemainOrder(Long id){
    Order order = orderMapper.selectById(id);
    if (order==null){
      log.info("根据订单id{},查询不到该订单信息!",id);
      throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此订单信息!");
    }
    OrderPayInfo orderPayInfo = new OrderPayInfo();
    orderPayInfo.setMemberId(SecurityKit.currentId());
    orderPayInfo.setOrderNo(order.getOrderNo());
    orderPayInfo.setStatus(0);//设置为未支付
    orderPayInfo.setType(1);//设置为首付款
    orderPayInfo.setPayAmount(order.getDownPaymentAmount());
    orderPayInfo.setUpdateTime(DateUtil.date());
    orderPayInfo.setCreateTime(DateUtil.date());
    orderPayInfoMapper.insert(orderPayInfo);
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
  public OrderItemVo createOrderItem(OrderItemDto orderItemDto){
    OrderItem orderItem = new OrderItem();
    BeanUtil.copyProperties(orderItemDto,orderItem);
    orderItem.setOrderItemNo(SUB_ORDER+idWorkerUtil.nextId());
    orderItem.setCreateTime(DateUtil.date());
    orderItem.setUpdateTime(DateUtil.date());
    ServiceType serviceType = serviceTypeMapper.selectById(orderItemDto.getServiceId());
    if (serviceType==null){
      log.info("根据服务id{},查询不到此服务");
      throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此服务!");
    }
    orderItem.setServiceName(serviceType.getName());
    orderItem.setMinMoney(serviceType.getMinMoney());
    if (orderItemDto.getSpecServiceId()!=null){
      orderItem.setOrderItemStatus(OrderStatusEnum.NO_PAID.getCode());
      SpecServicesPriceSimpleVo specServicesPrice =specServicesPriceMapper.findById(orderItemDto.getSpecServiceId());
      if (specServicesPrice==null){
        log.info("根据商品规格id{}查询商品规格信息不存在!");
        throw new LogicException(ResultEnum.DATA_ERROR,"查询商品规格信息不存在!");
      }
      orderItem.setCurrentUnitPrice(specServicesPrice.getPrice());
      if (specServicesPrice.getDownPaymentRate()!=null){
        orderItem.setDownPaymentRate(specServicesPrice.getDownPaymentRate());
      }else {
        String downPayRate = dictMapper.findValueByKey(DictEnum.DOWN_PAY_RATE.getKey());
        orderItem.setDownPaymentRate(new BigDecimal(downPayRate));
      }
      orderItem.setTotalPrice(specServicesPrice.getPrice().multiply(new BigDecimal(orderItemDto.getQuantity())));
      orderItem.setDownPaymentAmount(orderItem.getTotalPrice().multiply(specServicesPrice.getDownPaymentRate()));
      orderItem.setRemainAmount(orderItem.getTotalPrice().subtract(orderItem.getDownPaymentAmount()));
    }else {
      String downPayRate = dictMapper.findValueByKey(DictEnum.DOWN_PAY_RATE.getKey());
      orderItem.setDownPaymentRate(new BigDecimal(downPayRate));
      orderItem.setOrderItemStatus(OrderStatusEnum.WAIT.getCode());
    }
    orderItemMapper.insert(orderItem);
    return new OrderItemVo(orderItem);
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
    orderMapper.updateById(order);
    return new OrderSimpleVo(order);
  }

  public OrderPaymentVo payOrderXcx(Long id){
    Order order = orderMapper.selectById(id);
    if (order==null){
      log.info("订单id{},查询不到该订单!",id);
      throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此订单!");
    }
    OrderPayInfo orderPayInfo = orderPayInfoMapper.findOrderPayByOrderNo(Kv.by("orderNo",order.getOrderNo()).set("type",0));
    if (!Objects.equals(orderPayInfo.getStatus(),0)){
      log.info("订单首付款已经支付,无需重复发起支付!");
      throw new LogicException(ResultEnum.DATA_ERROR,"订单首付款已经支付,无需重复发起支付!");
    }
    // 2. 生成推送微信单号
    String wxNum = UtilKit.createNum("WX");
    orderPayInfo.setWxOrderNum(wxNum);
    orderPayInfo.setUpdateTime(DateUtil.date());
    orderPayInfoMapper.updateById(orderPayInfo);
    JwtUser jwtUser = SecurityKit.currentUser();
    assert jwtUser != null;
    JSONObject jsonObject =  JSONObject.parseObject(jwtUser.getMember().getAccount().getExtInfo());
    String openId =StringUtils.EMPTY;
    if (jsonObject!=null){
      openId = jsonObject.getString("openId");
    }
    // 3. 下单
    Map map = weiXinXcxService.weixinPay(
      UtilKit.currentIp(),                    // ip
      wxNum,                                  // 微信单号
      MoneyKit.getFen(orderPayInfo.getPayAmount()),    // 金额
      openId,      // OPEN ID
      orderMapper.getOrderInfo(order.getOrderNo()),// 构造商品明细
      true,
      false
    );
    // 4. 构造返回值
    return new OrderPaymentVo(id, map);
  }

  public OrderPaymentVo payRemainOrderXcx(Long id){
    Order order = orderMapper.selectById(id);
    if (order==null){
      log.info("订单id{},查询不到该订单!",id);
      throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此订单!");
    }
    OrderPayInfo orderPayInfo = orderPayInfoMapper.findOrderPayByOrderNo(Kv.by("orderNo",order.getOrderNo()).set("type",1));
    if (!Objects.equals(orderPayInfo.getStatus(),0)){
      log.info("订单首付款已经支付,无需重复发起支付!");
      throw new LogicException(ResultEnum.DATA_ERROR,"订单首付款已经支付,无需重复发起支付!");
    }
    // 2. 生成推送微信单号
    String wxNum = UtilKit.createNum("WX");
    orderPayInfo.setWxOrderNum(wxNum);
    orderPayInfo.setUpdateTime(DateUtil.date());
    orderPayInfoMapper.updateById(orderPayInfo);
    JwtUser jwtUser = SecurityKit.currentUser();
    assert jwtUser != null;
    // 3. 下单
    Map map = weiXinXcxService.weixinPay(
      UtilKit.currentIp(),                    // ip
      wxNum,                                  // 微信单号
      MoneyKit.getFen(orderPayInfo.getPayAmount()),    // 金额
      jwtUser.getMember().getAccount().getAccount(),      // OPEN ID
      orderMapper.getOrderInfo(order.getOrderNo()),// 构造商品明细
      true,
      true
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
      OrderItem orderItem = orderItemMapper.selectById(orderItemCommentsDto.getId());
      if (orderItem==null){
        log.info("根据订单明细id:{},查询不到此订单明细!");
        throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此订单明细");
      }
      OrderComments orderComments = new OrderComments();
      orderComments.setMemberId(SecurityKit.currentId());
      orderComments.setProviderId(orderItem.getProviderId());
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

  /**
   * 处理微信的异步回调通知
   *
   * @return
   */
  public void handlerWeixinNotify(String xmlStr) {
    // 1. XML 转JSON
    log.info("微信异步通知 XML:\n{}", xmlStr);
    cn.hutool.json.JSONObject json = JSONUtil.xmlToJson(xmlStr);

    // 2. XML 转化为JSON
    log.info("微信异步通知 XML->JSON: \n {}", JSONUtil.toJsonPrettyStr(json));

    // 3. 解析JSON
    String wxNum = UtilKit.getTargetByExpression("xml.out_trade_no", json);

    log.info("wxNum value:{}",wxNum);
    String success = UtilKit.getTargetByExpression("xml.result_code", json);

    // 4. 支付成功
    if ("SUCCESS".equals(success)) {
      OrderPayInfo orderPayInfo = orderPayInfoMapper.findOrderPayByWxNum(wxNum);
      if (orderPayInfo != null && Objects.equals(0, orderPayInfo.getStatus())) {
        // 4.1 设置订单为已付款
        orderPayInfo.setUpdateTime(DateUtil.date());
        orderPayInfo.setStatus(1);
        orderPayInfo.setPaymentTime(DateUtil.date());
        orderPayInfoMapper.updateById(orderPayInfo);

        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq(Order.ORDER_NO, orderPayInfo.getOrderNo()));
        order.setStatus(OrderStatusEnum.NO_REMAIN_PAID.getCode());
        order.setUpdateTime(DateUtil.date());
        orderMapper.updateById(order);
        orderItemMapper.updateOrderItemStatus(order.getOrderNo(),OrderStatusEnum.NO_REMAIN_PAID.getCode());
        //推送模版消息
        //mpTemplateService.send(order,MPTemplateEnum.PAY);
      }
    }
  }

  /**
   * 处理微信的异步回调通知
   *
   * @return
   */
  public void handlerWeixinRemainNotify(String xmlStr) {
    // 1. XML 转JSON
    log.info("微信异步通知 XML:\n{}", xmlStr);
    cn.hutool.json.JSONObject json = JSONUtil.xmlToJson(xmlStr);

    // 2. XML 转化为JSON
    log.info("微信异步通知 XML->JSON: \n {}", JSONUtil.toJsonPrettyStr(json));

    // 3. 解析JSON
    String wxNum = UtilKit.getTargetByExpression("xml.out_trade_no", json);
    String success = UtilKit.getTargetByExpression("xml.result_code", json);

    // 4. 支付成功
    if ("SUCCESS".equals(success)) {
      OrderPayInfo orderPayInfo = orderPayInfoMapper.findOrderPayByWxNum(wxNum);
      if (orderPayInfo != null && Objects.equals(0, orderPayInfo.getStatus())) {
        // 4.1 设置订单为已付款
        orderPayInfo.setUpdateTime(DateUtil.date());
        orderPayInfo.setStatus(1);
        orderPayInfo.setPaymentTime(DateUtil.date());
        orderPayInfoMapper.updateById(orderPayInfo);
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq(Order.ORDER_NO, orderPayInfo.getOrderNo()));
        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setUpdateTime(DateUtil.date());
        orderMapper.updateById(order);
        orderItemMapper.updateOrderItemStatus(order.getOrderNo(),OrderStatusEnum.PAID.getCode());
        //推送模版消息
        //mpTemplateService.send(order,MPTemplateEnum.PAY);
      }
    }
  }

  public void checkPayStatus(Long id){
    // 1. 验证订单
    Order order = orderMapper.selectById(id);
    if (order == null)
      throw new LogicException(ResultEnum.DATA_ERROR, "找不到订单ID为:" + id + "的订单!");
    OrderPayInfo orderPayInfo = null;
    Integer orderStatus = OrderStatusEnum.NO_REMAIN_PAID.getCode();
    // 2. 如果为待付款
    if (Objects.equals(order.getStatus(), OrderStatusEnum.NO_PAID.getCode())) {
      // 2.1 去查询
      orderPayInfo = orderPayInfoMapper.findOrderPayByOrderNo(Kv.by("orderNo",order.getOrderNo()).set("type",0));
      if (orderPayInfo!=null&&!Objects.equals(orderPayInfo.getStatus(),0)){
        log.info("订单首付款已经支付,无需重复发起支付!");
        throw new LogicException(ResultEnum.DATA_ERROR,"订单首付款已经支付,无需重复发起支付!");
      }
    }else if (Objects.equals(order.getStatus(), OrderStatusEnum.NO_REMAIN_PAID.getCode())){
      orderPayInfo = orderPayInfoMapper.findOrderPayByOrderNo(Kv.by("orderNo",order.getOrderNo()).set("type",1));
      if (orderPayInfo!=null&&!Objects.equals(orderPayInfo.getStatus(),0)){
        log.info("订单尾款已经支付,无需重复发起支付!");
        throw new LogicException(ResultEnum.DATA_ERROR,"订单尾款已经支付,无需重复发起支付!");
      }
      orderStatus = OrderStatusEnum.PAID.getCode();
    }
    String wxNum = orderPayInfo.getWxOrderNum();
    if (StrUtil.isBlank(wxNum))
      throw new LogicException(ResultEnum.DATA_ERROR, "未查询到相关微信订单!");
    // 2.2 确认返回结果
    Map ret = weiXinXcxService.weixinPayQuery(wxNum);
    if ("SUCCESS".equals(ret.get("trade_state"))) {
      orderPayInfo.setStatus(1);
      orderPayInfo.setUpdateTime(DateUtil.date());
      orderPayInfo.setPaymentTime(DateUtil.date());
      orderPayInfoMapper.updateById(orderPayInfo);
      order.setStatus(orderStatus);
      order.setUpdateTime(DateUtil.date());
      orderMapper.updateById(order);
      orderItemMapper.updateOrderItemStatus(order.getOrderNo(),orderStatus);
    } else {
      throw new LogicException(ResultEnum.DATA_ERROR, "付款失败请重试!");
    }
  }

}
