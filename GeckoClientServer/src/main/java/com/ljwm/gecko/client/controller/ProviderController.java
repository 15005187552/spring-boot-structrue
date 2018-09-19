package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.model.dto.ProviderDto;
import com.ljwm.gecko.base.model.dto.ProviderQueryDto;
import com.ljwm.gecko.base.model.vo.ProviderSimpleVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.base.service.ProviderService;
import com.ljwm.gecko.client.service.ClientProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provider")
@Api(tags = "服务商入驻管理 API")
public class ProviderController extends BaseController {

  @Autowired
  private ClientProviderService clientProviderService;

  @PostMapping("saveProvider")
  @ApiOperation(value = "服务商入驻")
  public Result saveProvider(@RequestBody ProviderDto providerDto){
    providerDto.setMemberId(SecurityKit.currentId());
    clientProviderService.saveProvider(providerDto);
    return success();
  }

  @PostMapping("findClient")
  @ApiOperation("小程序端查询服务商列表")
  public Result<ProviderSimpleVo> findClient(@RequestBody ProviderQueryDto providerQueryDto){
    return success(clientProviderService.findClientByPage(providerQueryDto));
  }

  @GetMapping("findProvider")
  @ApiOperation("根据会员id查询服务商信息")
  public Result<ProviderVo> findProviderByMemberId(){
    return success(clientProviderService.findProviderByMemberId(SecurityKit.currentId()));
  }

  @GetMapping("findProviderByProviderId/{id}")
  @ApiOperation("客户端根据服务商id查询服务商详情")
  public Result<ProviderVo> findProviderByProviderId(@PathVariable Long id){
    return success(clientProviderService.findProviderByProviderId(id));
  }

}
