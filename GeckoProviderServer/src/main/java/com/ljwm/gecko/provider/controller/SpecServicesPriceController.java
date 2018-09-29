package com.ljwm.gecko.provider.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.ProviderServicesVo;
import com.ljwm.gecko.base.model.vo.SpecServicesPriceSimpleVo;
import com.ljwm.gecko.provider.model.form.SpecServicesPriceCommonQueryForm;
import com.ljwm.gecko.provider.model.form.SpecServicesPriceForm;
import com.ljwm.gecko.provider.service.SpecServicesPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specServicesPrice")
@Api(tags = "服务规格管理")
public class SpecServicesPriceController extends BaseController {

  @Autowired
  private SpecServicesPriceService specServicesPriceService;

  @PostMapping("save")
  @ApiOperation("保存商品规格信息")
  public Result save(@RequestBody SpecServicesPriceForm specServicesPriceForm){
    specServicesPriceService.save(specServicesPriceForm);
    return success();
  }

  @PostMapping("find")
  @ApiOperation("查询商品的规格信息")
  public Result<List<SpecServicesPriceSimpleVo>> find(@RequestBody SpecServicesPriceCommonQueryForm specServicesPriceCommonQueryForm){
    return success(specServicesPriceService.find(specServicesPriceCommonQueryForm));
  }

  @GetMapping("findProviderServicesListByProviderId/{providerId}")
  @ApiOperation("根据服务商查询商品规格")
  public Result<List<ProviderServicesVo>>  findProviderServicesListByProviderId(@PathVariable Long providerId){
    return success(specServicesPriceService.findProviderServicesListByProviderId(providerId));
  }
}
