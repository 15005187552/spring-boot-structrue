package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.ConfirmProviderDto;
import com.ljwm.gecko.base.model.dto.ProviderQueryDto;
import com.ljwm.gecko.base.model.dto.ServeDto;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.base.model.vo.ServeSimpleVo;
import com.ljwm.gecko.base.model.vo.ServiceVo;
import com.ljwm.gecko.base.service.ProviderService;
import com.ljwm.gecko.base.service.ServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider")
@Api(tags = "服务商管理 API")
public class ProviderController extends BaseController {

  @Autowired
  private ServiceService serviceService;

  @Autowired
  private ProviderService providerService;

  @PostMapping("findByPage")
  @ApiOperation("查询服务商---带分页")
  public Result<Page<ProviderVo>> findByPage(@RequestBody ProviderQueryDto providerQueryDto){
    return success(providerService.findByPage(providerQueryDto));
  }

  @PostMapping("confirmProvider")
  @ApiOperation("审核服务商入驻申请")
  public Result confirmProvider(@RequestBody ConfirmProviderDto confirmProviderDto){
    providerService.confirmProvider(confirmProviderDto);
    return success();
  }

  @GetMapping("find")
  @ApiOperation("获取服务类型树")
  public Result<List<ServiceVo>> find(){
    return success(serviceService.find());
  }

  @PostMapping("save")
  @ApiOperation("保存服务类型")
  public Result<ServeSimpleVo> save(@RequestBody ServeDto serveDto){
    return success(serviceService.save(serveDto));
  }
}
