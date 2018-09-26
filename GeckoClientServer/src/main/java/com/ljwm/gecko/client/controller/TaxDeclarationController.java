package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.RecordForm;
import com.ljwm.gecko.client.model.dto.TaxForm;
import com.ljwm.gecko.client.model.dto.TaxInfoForm;
import com.ljwm.gecko.client.model.dto.TaxListForm;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.TaxDeclarationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/9/5 16:11
 */
@RestController
@RequestMapping("/tax")
@Api(tags = "个税申报相关 API")
public class TaxDeclarationController {

  @Autowired
  TaxDeclarationService taxDeclarationService;

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/commitInfo")
  @ApiOperation("提交信息")
  public Result commit(@RequestBody @Valid TaxForm taxForm){
    return taxDeclarationService.commit(taxForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/findInfo")
  @ApiOperation("查看信息")
  public Result findInfo(@RequestBody @Valid TaxInfoForm taxInfoForm){
    return Result.success(taxDeclarationService.find(taxInfoForm));
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/declareType")
  @ApiOperation("申报类型")
  public Result declareType(@RequestBody @Valid RecordForm recordForm){
    return taxDeclarationService.declareType(recordForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/findTaxList")
  @ApiOperation("查看信息")
  public Result findTaxList(@RequestBody @Valid TaxListForm taxListForm){
    return taxDeclarationService.findTaxList(taxListForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("findTaxById/{taxId}")
  @ApiOperation("查看账单详情")
  public Result findTaxById(@PathVariable Long taxId){
    return taxDeclarationService.findTaxById(taxId);
  }


}
