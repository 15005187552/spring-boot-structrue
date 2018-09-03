package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.service.LocationRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/locationRate")
@Api(tags = "地区利率管理  PI")
@Slf4j
public class LocationRateController extends BaseController {

  @Autowired
  private LocationRateService locationRateService;

  @PostMapping("uploadLocationRate")
  @ApiOperation(value = "上传地区税率")
  public Result uploadLocationRate(@RequestParam("file") MultipartFile multipartFile){
    locationRateService.uploadLocationRate(multipartFile);
    return success();
  }
}
