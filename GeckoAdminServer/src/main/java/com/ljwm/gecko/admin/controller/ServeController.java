package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.ServiceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/serve")
@Api(tags = "服务类型管理 API")
public class ServeController extends BaseController {

  @Autowired
  private ServeService serveService;

  @GetMapping("find")
  @ApiOperation("获取服务类型树")
  public Result<List<ServiceVo>> find(){
    return success(serveService.find());
  }
}
