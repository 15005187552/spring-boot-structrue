package com.ljwm.gecko.provider.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.OrderQueryDto;
import com.ljwm.gecko.base.model.vo.OrderVo;
import com.ljwm.gecko.provider.model.form.OrderItemPriceDto;
import com.ljwm.gecko.provider.service.OrderProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Api(tags = "服务商订单管理  API")
public class OrderController extends BaseController {

  @Autowired
  private OrderProviderService orderProviderService;

  @PostMapping("find")
  @ApiOperation("服务商查询订单列表")
  public Result<Page<OrderVo>> find(@RequestBody OrderQueryDto orderQueryDto){
    return success(orderProviderService.find(orderQueryDto));
  }

  @PostMapping("resetPrice")
  @ApiOperation("服务商定价")
  public Result resetPrice(@RequestBody OrderItemPriceDto orderItemPriceDto){
    orderProviderService.resetPrice(orderItemPriceDto);
    return success();
  }
}
