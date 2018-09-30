package com.ljwm.gecko.provider.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.SpecVo;
import com.ljwm.gecko.provider.service.ProviderSpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spec")
@Api(tags = "服务商规格管理 API")
public class ProviderSpecController extends BaseController {

  @Autowired
  private ProviderSpecService providerSpecService;

  @PostMapping("findByServiceId/{serviceId}")
  @ApiOperation("服务规格--查询列表不带分页")
  public Result<List<SpecVo>> findByServiceId(@PathVariable Integer serviceId){
    return success(providerSpecService.findByServiceId(serviceId));
  }
}
