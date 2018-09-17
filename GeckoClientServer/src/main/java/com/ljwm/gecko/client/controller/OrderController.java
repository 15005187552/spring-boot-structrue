package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.OrderDto;
import com.ljwm.gecko.base.model.dto.OrderItemDto;
import com.ljwm.gecko.base.model.vo.OrderVo;
import com.ljwm.gecko.client.service.ClientOrderService;
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
  private ClientOrderService clientOrderService;

  @PostMapping("placeOrder")
  @ApiOperation("创建支付订单")
  public Result<OrderVo> placeOrder(@RequestBody OrderDto orderDto){
    return success(clientOrderService.placeOrder(orderDto));
  }

  @PostMapping("createOrderItem")
  @ApiOperation("创建子订单")
  public Result createOrderItem(@RequestBody OrderItemDto orderItemDto){
    clientOrderService.createOrderItem(orderItemDto);
    return success();
  }
}
