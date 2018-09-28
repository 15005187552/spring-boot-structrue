package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.gecko.client.service.ClientSpecServicesPrice;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specServicePrice")
@Api(tags = "小程序商品规格 API")
public class SpecServicePriceController extends BaseController {

  @Autowired
  private ClientSpecServicesPrice clientSpecServicesPrice;

}
