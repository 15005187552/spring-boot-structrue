package com.ljwm.gecko.client.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.ProviderGoodsVo;
import com.ljwm.gecko.client.model.dto.ProviderGoodQueryDto;
import com.ljwm.gecko.client.service.ClientProviderGoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
@Api("小程序服务商品 API")
public class GoodsController extends BaseController {

  @Autowired
  private ClientProviderGoodService clientProviderGoodService;

  @PostMapping("findGoodsListByServiceId")
  @ApiOperation("根据服务类型查询商品列表")
  public Result<Page<ProviderGoodsVo>> findGoodsListByServiceId(@RequestBody ProviderGoodQueryDto providerGoodQueryDto){
    return success(clientProviderGoodService.findGoodsListByServiceId(providerGoodQueryDto));
  }
}
