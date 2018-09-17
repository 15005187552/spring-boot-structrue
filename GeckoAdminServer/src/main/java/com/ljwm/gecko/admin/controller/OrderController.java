package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.OrderVo;
import com.ljwm.gecko.base.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Api(tags = "订单管理 API")
public class OrderController extends BaseController {

  @Autowired
  private OrderService orderService;

  @PostMapping("find")
  @ApiOperation("查询订单列表")
  public Result<Page<OrderVo>>  find(){
    return success();
  }
}
