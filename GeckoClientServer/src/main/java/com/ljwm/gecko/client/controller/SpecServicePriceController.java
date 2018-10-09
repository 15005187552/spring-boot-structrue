package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.SpecServicePriceQueryForm;
import com.ljwm.gecko.client.service.ClientSpecServicesPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specServicePrice")
@Api(tags = "小程序商品规格 API")
public class SpecServicePriceController extends BaseController {

  @Autowired
  private ClientSpecServicesPriceService clientSpecServicesPriceService;


  @PostMapping("findSpecServicePrice")
  @ApiOperation("查询商品的规格信息")
  public Result findSpecServicePrice(@RequestBody SpecServicePriceQueryForm specServicePriceQueryForm){
      return success(clientSpecServicesPriceService.findSpecServicesPrice(specServicePriceQueryForm));
  }

  @GetMapping("findProviderKeys/{serviceId}/{providerId}")
  public Result<List<String>> findProviderKeys(@PathVariable Integer serviceId,@PathVariable Long providerId){
    return success(clientSpecServicesPriceService.findProviderKeys(serviceId,providerId));
  }

}
