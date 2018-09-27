package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.service.SpecItemService;
import com.ljwm.gecko.base.model.dto.SpecItemCommonQueryDto;
import com.ljwm.gecko.base.model.dto.SpecItemDto;
import com.ljwm.gecko.base.model.dto.SpecItemQueryDto;
import com.ljwm.gecko.base.model.vo.SpecItemSimpleVo;
import com.ljwm.gecko.base.model.vo.SpecItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specItem")
@Api(tags = "规格项管理 API")
public class SpecItemController extends BaseController {

  @Autowired
  private SpecItemService specItemService;

  @PostMapping("findByPage")
  @ApiOperation("服务规格--查询列表带分页")
  public Result<Page<SpecItemVo>> findByPage(@RequestBody SpecItemQueryDto specItemQueryDto){
    return success(specItemService.findByPage(specItemQueryDto));
  }

  @PostMapping("find")
  @ApiOperation("服务规格--查询列表不带分页")
  public Result<List<SpecItemSimpleVo>> find(@RequestBody SpecItemCommonQueryDto specItemCommonQueryDto){
    return success(specItemService.find(specItemCommonQueryDto));
  }

  @PostMapping("save")
  @ApiOperation("服务规格--保存或修改")
  public Result save(@RequestBody SpecItemDto specItemDto){
    specItemService.save(specItemDto);
    return success();
  }
}
