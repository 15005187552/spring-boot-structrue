package com.ljwm.gecko.provider.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.provider.service.ProviderProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/provider")
@Api(tags = "服务商管理 API")
public class ProviderController extends BaseController {

  @Autowired
  private ProviderProviderService providerProviderService;

  @GetMapping("findProviderListByMemberId/{memberId}")
  @ApiOperation("根据会员id查询当前所属的服务商")
  public Result<List<ProviderVo>>  findProviderListByMemberId(@PathVariable Long memberId){
    return success(providerProviderService.findProviderListByMemberId(memberId));
  }

  @GetMapping("findProviderByProviderId/{id}")
  @ApiOperation("客户端根据服务商id查询服务商详情")
  public Result<ProviderVo> findProviderByProviderId(@PathVariable Long id){
    return success(providerProviderService.findProviderByProviderId(id));
  }

  @GetMapping("disabled/{id}/{status}")
  public Result disabled(@PathVariable Long id,@PathVariable Integer status){
    return success(providerProviderService.disabled(id,status));
  }
}
