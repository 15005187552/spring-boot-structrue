package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.CompanyForm;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/commit")
  @ApiOperation("提交公司信息（包含修改）")
  public Result commit(@RequestBody @Valid CompanyForm companyForm){
    return companyService.commit(companyForm);
  }

  @PreAuthorize(JwtUser.HAS_MEMBER_ROLE)
  @PostMapping("/findByName")
  @ApiOperation("根据公司名搜索审核通过的公司")
  public Result findByName(@RequestParam("name") String name){
    return Result.success(companyService.findByName(name));
  }

  @PostMapping("/findEmployee")
  @ApiOperation("根据公司名搜索员工")
  public Result findEmployee(@RequestParam("companyId") Long companyId){
    return companyService.findEmployee(companyId);
  }

  @PostMapping("/findCompanyById")
  @ApiOperation("根据公司id搜索公司信息")
  public Result findCompanyById(@RequestParam("companyId") Long companyId){
    return Result.success(companyService.findCompanyById(companyId));
  }

  /*@PostMapping("/postSpecial")
  @ApiOperation("上传公司五险一金项")
  public Result postSpecial(@RequestBody SpecailForm specailForm){
    return companyService.postSpecial(specailForm);
  }*/

}
