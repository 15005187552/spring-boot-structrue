package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.ServeSimpleVo;
import com.ljwm.gecko.base.service.ServiceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("serviceType")
@Api(tags = "服务类型客户端 API")
public class ServiceTypeController extends BaseController {

  @Autowired
  private ServiceTypeService serviceTypeService;

  @GetMapping("find")
  @ApiOperation("查询服务类型树")
  public Result find(){
    return success(serviceTypeService.find());
  }


  @GetMapping("findTopList")
  @ApiOperation("查询置顶服务类型")
  public Result<List<ServeSimpleVo>> findTopList(){
    return success(serviceTypeService.findTopList());
  }
}
