package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.TaxForm;
import com.ljwm.gecko.client.service.TaxDeclarationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @PostMapping("/commitInfo")
  @ApiOperation("提交信息")
  public Result commit(@RequestBody @Valid TaxForm taxForm){
    return taxDeclarationService.commit(taxForm);
  }

}
