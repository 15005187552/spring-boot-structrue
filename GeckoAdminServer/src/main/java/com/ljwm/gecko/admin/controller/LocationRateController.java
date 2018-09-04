package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.LocationRateQuery;
import com.ljwm.gecko.admin.model.form.RateSaveForm;
import com.ljwm.gecko.admin.service.LocationRateService;
import com.ljwm.gecko.base.model.vo.SimpleLocation;
import com.ljwm.gecko.base.model.vo.SimpleProv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/locationRate")
@Api(tags = "地区利率管理  PI")
@Slf4j
public class LocationRateController extends BaseController {

  @Autowired
  private LocationRateService locationRateService;

  @PostMapping("uploadLocationRate")
  @ApiOperation(value = "上传地区税率")
  public Result uploadLocationRate(@RequestParam("file") MultipartFile multipartFile) {
    locationRateService.uploadLocationRate(multipartFile);
    return success();
  }

  @GetMapping("getProvAndCity")
  @ApiOperation(value = "获取所有省市")
  public Result<List<SimpleLocation>> getProvAndCity() {
    return success(locationRateService.getProvAndCity());
  }

  @GetMapping("findProv")
  @ApiOperation(value = "获取所有省")
  public Result<List<SimpleProv>> findProv() {
    return success(locationRateService.findProv());
  }

  @PostMapping("find")
  @ApiOperation("分页查询获取市")
  public Result find(@RequestBody LocationRateQuery query) {
    return success(locationRateService.find(query));
  }


  @PostMapping("saveRate")
  @ApiOperation("保存税率")
  public Result saveRate(@RequestBody RateSaveForm form) {
    return success(locationRateService.saveRate(form));
  }

  @GetMapping("deleteRate/{id}")
  public Result deleteRate(@PathVariable Long id) {
    return success(locationRateService.deleteRate(id));
  }
}
