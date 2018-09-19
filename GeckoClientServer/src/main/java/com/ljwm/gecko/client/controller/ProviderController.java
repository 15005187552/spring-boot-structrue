package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.model.dto.ProviderDto;
import com.ljwm.gecko.base.model.dto.ProviderQueryDto;
import com.ljwm.gecko.base.model.vo.ProviderSimpleVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.base.service.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provider")
@Api(tags = "服务商入驻管理 API")
public class ProviderController extends BaseController {

  @Autowired
  private ProviderService providerService;

  @PostMapping("saveProvider")
  @ApiOperation(value = "服务商入驻")
  public Result saveProvider(@RequestBody ProviderDto providerDto){
    providerDto.setMemberId(SecurityKit.currentId());
    providerService.saveProvider(providerDto);
    return success();
  }

  @PostMapping("findClient")
  @ApiOperation("小程序端查询服务商列表")
  public Result<ProviderVo> findClient(@RequestBody ProviderQueryDto providerQueryDto){
    return success(providerService.findByPage(providerQueryDto));
  }

  @GetMapping("findProvider")
  @ApiOperation("根据会员id查询服务商信息")
  public Result<ProviderSimpleVo> findProviderByMemberId(){
    return success(providerService.findProviderByMemberId(SecurityKit.currentId()));
  }
}
