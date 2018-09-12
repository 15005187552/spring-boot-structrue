package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.OrderDto;
import com.ljwm.gecko.base.model.dto.OrderItemDto;
import com.ljwm.gecko.base.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@Api(tags = "订单管理--客户端  API")
public class OrderController extends BaseController {

  @Autowired
  private OrderService orderService;

  @PostMapping("createOrder")
  @ApiOperation("创建支付订单")
  public Result createOrder(@RequestBody OrderDto orderDto){
    orderService.createOrder(orderDto);
    return success();
  }

  @PostMapping("createOrderItem")
  @ApiOperation("创建子订单")
  public Result createOrderItem(@RequestBody OrderItemDto orderItemDto){
    orderService.createOrderItem(orderItemDto);
    return success();
  }
}
