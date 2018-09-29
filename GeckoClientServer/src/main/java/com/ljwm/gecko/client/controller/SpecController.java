package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.SpecCommonQueryDto;
import com.ljwm.gecko.base.model.vo.SpecVo;
import com.ljwm.gecko.client.service.ClientSpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spec")
@Api(tags = "小程序规格管理 API")
public class SpecController extends BaseController {

  @Autowired
  private ClientSpecService clientSpecService;

  @PostMapping("findByServiceId/{serviceId}")
  @ApiOperation("服务规格--查询列表不带分页")
  public Result<List<SpecVo>> findByServiceId(@PathVariable Integer serviceId){
    return success(clientSpecService.findByServiceId(serviceId));
  }
}
