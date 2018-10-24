package com.ljwm.gecko.client.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfinal.kit.HttpKit;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.model.vo.OrderItemVo;
import com.ljwm.gecko.base.model.vo.OrderSimpleVo;
import com.ljwm.gecko.base.model.vo.OrderVo;
import com.ljwm.gecko.client.model.vo.OrderPaymentVo;
import com.ljwm.gecko.client.service.ClientOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("order")
@Api(tags = "订单管理--客户端  API")
@Slf4j
public class OrderController extends BaseController {

  @Autowired
  private ClientOrderService clientOrderService;

  @PostMapping("createOrderItem")
  @ApiOperation("前台--① 创建子订单")
  public Result<OrderItemVo> createOrderItem(@RequestBody OrderItemDto orderItemDto){
    orderItemDto.setMemberId(SecurityKit.currentId());
    return success(clientOrderService.createOrderItem(orderItemDto));
  }

  @PostMapping("placeDownOrder")
  @ApiOperation("前台--②-1 创建首付款支付订单")
  public Result<OrderVo> placeDownOrder(@RequestBody OrderDto orderDto){
    return success(clientOrderService.placeOrder(orderDto));
  }

  @GetMapping("placeDownOrder/{id}")
  @ApiOperation("前台--②-2 创建尾款支付订单")
  public Result<OrderVo> placeRemainOrder(@PathVariable Long id){
    return success(clientOrderService.placeRemainOrder(id));
  }

  @GetMapping("payOrderTest/{id}")
  @ApiOperation("前台--③-1  开发环境直接设置为已支付")
  public Result<OrderSimpleVo> payOrderTest(@PathVariable @Valid Long id) {
    return success(clientOrderService.setOrderPaid(id));
  }

  @GetMapping("payDownOrder/{id}")
  @ApiOperation("前台--③-1 测试/正式 (小程序) 对首付款未付款的订单唤起微信支付")
  public Result<OrderPaymentVo> payOrderXcx(@PathVariable @Valid Long id) {
    return success(clientOrderService.payOrderXcx(id));
  }

  @GetMapping("payRemainOrder/{id}")
  @ApiOperation("前台--③-2 测试/正式 (小程序) 对尾款未付款的订单唤起微信支付")
  public Result<OrderPaymentVo> payRemainOrderXcx(@PathVariable @Valid Long id) {
    return success(clientOrderService.payRemainOrderXcx(id));
  }

  @GetMapping("checkPayStatus/{id}")
  @ApiOperation("前台--③-1 测试/正式 查询订单支付状态")
  public Result checkPayStatus(@PathVariable @Valid Long id) {
    clientOrderService.checkPayStatus(id);
    return success();
  }

  @PostMapping("findOrderItem")
  @ApiOperation("会员查询订单子列表")
  public Result<Page<OrderItemVo>> find(@RequestBody OrderItemQueryDto orderItemQueryDto){
    orderItemQueryDto.setMemberId(SecurityKit.currentId());
    return success(clientOrderService.findOrderItemList(orderItemQueryDto));
  }

  @PostMapping("findOrder")
  @ApiOperation("会员查询订单列表")
  public Result<Page<OrderVo>> find(@RequestBody OrderQueryDto orderQueryDto){
    orderQueryDto.setMemberId(SecurityKit.currentId());
    return success(clientOrderService.findOrderList(orderQueryDto));
  }

  @PostMapping("comments")
  @ApiOperation("客户端 --- 评论订单")
  public Result<OrderSimpleVo> comments(@RequestBody @Valid OrderCommentsDto orderCommentsForm) {
    return success(clientOrderService.comments(orderCommentsForm));
  }

  @RequestMapping("weixin/notify")
  @ApiOperation(hidden = true, value = "处理微信回调接口!")
  public void handlerWeixinNotify(HttpServletRequest request) {
    log.info("处理微信回调接口  handlerWeixinNotify:{}");
    String xmlStr = HttpKit.readData(request);
    log.info("处理微信回调接口:{}",xmlStr);
    clientOrderService.handlerWeixinNotify(xmlStr);
  }

  @RequestMapping("weixin/remainNotify")
  @ApiOperation(hidden = true, value = "处理微信回调接口!")
  public void handlerWeixinRemainNotify(HttpServletRequest request) {
    log.info("处理微信回调接口  handlerWeixinRemainNotify:{}");
    String xmlStr = HttpKit.readData(request);
    log.info("处理微信回调接口:{}",xmlStr);
    clientOrderService.handlerWeixinRemainNotify(xmlStr);
  }
}
