package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.CompanyForm;
import com.ljwm.gecko.client.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/9/3 10:50
 */
@RestController
@RequestMapping("company")
@Api(tags = "公司信息 API")
public class CompanyController {

  @Autowired
  CompanyService companyService;

  @PostMapping("/commit")
  @ApiOperation("提交公司信息")
  public Result commit(@RequestBody @Valid CompanyForm companyForm){
    return Result.success(companyService.commit(companyForm));
  }

  @PostMapping("/uploadfile")
  @ApiOperation("提交公司信息")
  public Result commit(@RequestParam("updateFiles") MultipartFile[] files){
    return Result.success(companyService.upload(files));
  }
}
