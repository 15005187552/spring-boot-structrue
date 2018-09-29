package com.ljwm.gecko.client.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.model.dto.OrderCommentsDto;
import com.ljwm.gecko.base.model.dto.OrderDto;
import com.ljwm.gecko.base.model.dto.OrderItemDto;
import com.ljwm.gecko.base.model.dto.OrderItemQueryDto;
import com.ljwm.gecko.base.model.vo.OrderSimpleVo;
import com.ljwm.gecko.base.model.vo.OrderVo;
import com.ljwm.gecko.client.model.vo.OrderPaymentVo;
import com.ljwm.gecko.client.service.ClientOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("order")
@Api(tags = "订单管理--客户端  API")
public class OrderController extends BaseController {

  @Autowired
  private ClientOrderService clientOrderService;

  @PostMapping("placeOrder")
  @ApiOperation("创建支付订单")
  public Result<OrderVo> placeOrder(@RequestBody OrderDto orderDto){
    return success(clientOrderService.placeOrder(orderDto));
  }

  @PostMapping("createOrderItem")
  @ApiOperation("创建子订单")
  public Result createOrderItem(@RequestBody OrderItemDto orderItemDto){
    orderItemDto.setMemberId(SecurityKit.currentId());
    clientOrderService.createOrderItem(orderItemDto);
    return success();
  }

  @PostMapping("find")
  @ApiOperation("服务商查询订单列表")
  public Result<Page<OrderVo>> find(@RequestBody OrderItemQueryDto orderItemQueryDto){
    return success(clientOrderService.findOrderItemList(orderItemQueryDto));
  }

  @GetMapping("payOrderTest/{id}")
  @ApiOperation("客户端 开发环境直接设置为已支付")
  public Result<OrderSimpleVo> payOrderTest(@PathVariable @Valid Long id) {
    return success(clientOrderService.setOrderPaid(id));
  }

  @GetMapping("payOrder/{id}")
  @ApiOperation("客户端 测试/正式 (小程序) 对未付款的订单唤起微信支付")
  public Result<OrderPaymentVo> payOrderXcx(@PathVariable @Valid Long id) {
    return success(clientOrderService.payOrderXcx(id));
  }

  @PostMapping("comments")
  @ApiOperation("客户端 --- 评论订单")
  public Result<OrderSimpleVo> comments(@RequestBody @Valid OrderCommentsDto orderCommentsForm) {
    return success(clientOrderService.comments(orderCommentsForm));
  }
}
