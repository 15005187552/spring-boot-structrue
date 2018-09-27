package com.ljwm.gecko.provider.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.vo.ProviderGoodsVo;
import com.ljwm.gecko.provider.model.form.ProviderGoodsForm;
import com.ljwm.gecko.provider.model.form.ProviderGoodsQueryForm;
import com.ljwm.gecko.provider.service.ProviderGoodsProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/providerGood")
@Api(tags = "服务商商品管理 API")
public class ProviderGoodsController extends BaseController {

  @Autowired
  private ProviderGoodsProviderService providerGoodsProviderService;

  @PostMapping("findByPage")
  @ApiOperation("查询服务商下的商品列表--带分页")
  public Result<Page<ProviderGoodsVo>> findByPage(@RequestBody ProviderGoodsQueryForm providerGoodsQueryForm){
    return success(providerGoodsProviderService.findByPage(providerGoodsQueryForm));
  }

  @PostMapping("save")
  @ApiOperation("服务商保存商品信息")
  public Result save(@RequestBody ProviderGoodsForm providerGoodsForm){
    providerGoodsProviderService.save(providerGoodsForm);
    return success();
  }
}
