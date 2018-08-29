package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.model.dto.IncomeTypeDto;
import com.ljwm.gecko.base.model.dto.SpecialDeductionDto;
import com.ljwm.gecko.base.model.dto.SpecialDeductionQueryDto;
import com.ljwm.gecko.base.model.vo.IncomeTypeSimpleVO;
import com.ljwm.gecko.base.model.vo.IncomeTypeVO;
import com.ljwm.gecko.base.model.vo.SpecialDeductionVO;
import com.ljwm.gecko.base.service.IncomeTypeService;
import com.ljwm.gecko.base.service.SpecialDeductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reportData")
@Api(tags = "收入类型管理  API")
public class ReportDataController extends BaseController {

  @Autowired
  private IncomeTypeService incomeTypeService;

  @Autowired
  private SpecialDeductionService specialDeductionService;

  @GetMapping("findIncomeType")
  @ApiOperation(value = "查询收入类型列表")
  public Result<List<IncomeTypeVO>> findIncomeType(){
    return success(incomeTypeService.find());
  }

  @PostMapping("saveIncomeType")
  @ApiOperation(value ="保存收入类型" )
  public Result<IncomeTypeSimpleVO> saveIncomeType(@RequestBody IncomeTypeDto incomeTypeDto){
    return success(incomeTypeService.save(incomeTypeDto));
  }

  @GetMapping("deleteIncomeType/{id}")
  @ApiOperation(value = "根据id删除收入类型")
  public Result deleteIncomeType(@PathVariable Long id){
    incomeTypeService.delete(id);
    return success();
  }

  @PostMapping("saveSpecialDeduction")
  @ApiOperation(value = "专项扣除添加")
  public Result<SpecialDeductionVO> saveSpecialDeduction(@RequestBody SpecialDeductionDto specialDeductionDto){
    return success(specialDeductionService.save(specialDeductionDto));
  }

  @GetMapping("findSpecialDeduction")
  @ApiOperation(value = "查询专项扣除---不带分页")
  public Result<List<SpecialDeductionVO>> findSpecialDeduction(){
    return success(specialDeductionService.find());
  }

  @PostMapping("findSpecialDeductionPage")
  @ApiOperation(value = "查询专项扣除---带分页")
  public Result<Page<SpecialDeductionVO>> findSpecialDeductionPage(@RequestBody SpecialDeductionQueryDto specialDeductionQueryDto){
    return success(specialDeductionService.findPage(specialDeductionQueryDto));
  }
}
