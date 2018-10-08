package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.service.SpecService;
import com.ljwm.gecko.base.model.dto.SpecCommonQueryDto;
import com.ljwm.gecko.base.model.dto.SpecDto;
import com.ljwm.gecko.base.model.dto.SpecQueryDto;
import com.ljwm.gecko.base.model.vo.SpecSimpleVo;
import com.ljwm.gecko.base.model.vo.SpecVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spec")
@Api(tags = "服务规格 API")
public class SpecController extends BaseController {

  @Autowired
  private SpecService specService;

  @PostMapping("findByPage")
  @ApiOperation("服务规格--查询列表带分页")
  public Result<Page<SpecVo>> findByPage(@RequestBody SpecQueryDto specQueryDto){
    return success(specService.findByPage(specQueryDto));
  }

  @PostMapping("find")
  @ApiOperation("服务规格--查询列表不带分页")
  public Result<List<SpecSimpleVo>> find(@RequestBody SpecCommonQueryDto specCommonQueryDto){
    return success(specService.find(specCommonQueryDto));
  }

  @PostMapping("save")
  @ApiOperation("服务规格--保存或修改")
  public Result save(@RequestBody SpecDto specDto){
    specService.save(specDto);
    return success();
  }
}
