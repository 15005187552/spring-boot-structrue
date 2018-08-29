package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.IncomeTypeDto;
import com.ljwm.gecko.base.model.vo.IncomeTypeSimpleVO;
import com.ljwm.gecko.base.model.vo.IncomeTypeVO;
import com.ljwm.gecko.base.service.IncomeTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reportData")
@Api(tags = "收入类型管理  API")
public class ReportDataController extends BaseController {

  @Autowired
  private IncomeTypeService incomeTypeService;

  @GetMapping("findIncomeType")
  @ApiModelProperty(value = "查询收入类型列表")
  public Result<List<IncomeTypeVO>> findIncomeType(){
    return success(incomeTypeService.find());
  }

  @PostMapping("saveIncomeType")
  @ApiModelProperty(value ="保存收入类型" )
  public Result<IncomeTypeSimpleVO> saveIncomeType(@RequestBody IncomeTypeDto incomeTypeDto){
    return success(incomeTypeService.save(incomeTypeDto));
  }

  @GetMapping("deleteIncomeType/{id}")
  @ApiModelProperty(value = "根据id删除收入类型")
  public Result deleteIncomeType(@PathVariable Long id){
    incomeTypeService.delete(id);
    return success();
  }



}
