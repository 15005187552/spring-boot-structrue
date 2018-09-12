package com.ljwm.gecko.provider.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.ProviderCustomDto;
import com.ljwm.gecko.base.model.dto.ProviderCustomQueryDto;
import com.ljwm.gecko.base.model.vo.ProviderCustomVo;
import com.ljwm.gecko.base.service.ProviderCustomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/providerCustom")
@Api(tags = "服务商服务明细管理 API")
public class ProviderCustomController extends BaseController {

  @Autowired
  private ProviderCustomService providerCustomService;

  @PostMapping("save")
  @ApiOperation("保存产品服务明细")
  public Result save(@RequestBody ProviderCustomDto providerCustomDto){
    providerCustomService.save(providerCustomDto);
    return success("添加成功!");
  }

  @PostMapping("findByPage")
  @ApiOperation("查询服务商服务明细---带分页")
  public Result<Page<ProviderCustomVo>> findByPage(@RequestBody ProviderCustomQueryDto providerCustomQueryDto){
    return success(providerCustomService.findByPage(providerCustomQueryDto));
  }
}
