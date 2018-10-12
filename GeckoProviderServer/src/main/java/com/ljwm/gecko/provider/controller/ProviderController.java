package com.ljwm.gecko.provider.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import com.ljwm.gecko.provider.model.form.ProviderDetailDto;
import com.ljwm.gecko.provider.service.ProviderProviderService;
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
  @ApiOperation("启用/禁用")
  public Result disabled(@PathVariable Long id,@PathVariable Integer status){
    return success(providerProviderService.disabled(id,status));
  }

  @PostMapping("updateProviderDetail")
  @ApiOperation("更新服务商详情信息")
  public Result updateProviderDetail(@RequestBody ProviderDetailDto providerDetailDto){
    providerProviderService.updateProviderDetail(providerDetailDto);
    return success();
  }
}
