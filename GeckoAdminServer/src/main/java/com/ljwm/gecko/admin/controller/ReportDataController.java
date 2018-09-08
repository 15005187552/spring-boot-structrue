package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.OtherReduce;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.model.form.OtherReduceForm;
import com.ljwm.gecko.base.model.form.OtherReduceQuery;
import com.ljwm.gecko.base.model.vo.*;
import com.ljwm.gecko.base.service.AddSpecialService;
import com.ljwm.gecko.base.service.IncomeTypeService;
import com.ljwm.gecko.base.service.OtherReduceService;
import com.ljwm.gecko.base.service.SpecialDeductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reportData")
@Api(tags = "申报数据类型管理  API")
public class ReportDataController extends BaseController {

  @Autowired
  private IncomeTypeService incomeTypeService;

  @Autowired
  private SpecialDeductionService specialDeductionService;

  @Autowired
  private OtherReduceService otherReduceService;

  @Autowired
  private AddSpecialService addSpecialService;

  @GetMapping("findIncomeType")
  @ApiOperation(value = "查询收入类型列表--不带分页")
  public Result<List<IncomeTypeVo>> findIncomeType() {
    return success(incomeTypeService.find());
  }

  @ApiOperation(value = "查询收入类型---带分页")
  @PostMapping("findIncomeTypePage")
  public Result<Page<IncomeTypeVo>> findIncomeTypePage(@RequestBody IncomeTypeQueryDto incomeTypeQueryDto){
    return success(incomeTypeService.findPage(incomeTypeQueryDto));
  }

  @PostMapping("saveIncomeType")
  @ApiOperation(value ="保存收入类型" )
  public Result<IncomeTypeSimpleVo> saveIncomeType(@RequestBody IncomeTypeDto incomeTypeDto) {
    return success(incomeTypeService.save(incomeTypeDto));
  }

  @GetMapping("deleteIncomeType/{id}")
  @ApiOperation(value = "根据id删除收入类型")
  public Result deleteIncomeType(@PathVariable Long id) {
    incomeTypeService.delete(id);
    return success();
  }

  @PostMapping("saveSpecialDeduction")
  @ApiOperation(value = "专项扣除添加")
  public Result<SpecialDeductionVo> saveSpecialDeduction(@RequestBody SpecialDeductionDto specialDeductionDto) {
    return success(specialDeductionService.save(specialDeductionDto));
  }

  @GetMapping("findSpecialDeduction")
  @ApiOperation(value = "查询专项扣除---不带分页")
  public Result<List<SpecialDeductionVo>> findSpecialDeduction() {
    return success(specialDeductionService.find());
  }

  @PostMapping("findSpecialDeductionPage")
  @ApiOperation(value = "查询专项扣除---带分页")
  public Result<Page<SpecialDeductionVo>> findSpecialDeductionPage(@RequestBody SpecialDeductionQueryDto specialDeductionQueryDto) {
    return success(specialDeductionService.findPage(specialDeductionQueryDto));
  }

  @GetMapping("/deleteSpecialDeduction/{id}")
  @ApiOperation(value = "删除专项扣除")
  public Result deleteSpecialDeduction(@PathVariable Long id){
    specialDeductionService.delete(id);
    return success();
  }

  @PostMapping("saveAddSpecial")
  @ApiOperation(value = "专项附加扣除添加")
  public Result<AddSpecialVo> saveAddSpecial(@RequestBody AddSpecialDto addSpecialDto){
    return success(addSpecialService.save(addSpecialDto));
  }

  @GetMapping("findAddSpecial")
  @ApiOperation(value = "查询专项附加扣除---不带分页")
  public Result<List<AddSpecialVo>> findAddSpecial(){
    return success(addSpecialService.find());
  }

  @PostMapping("findAddSpecialPage")
  @ApiOperation(value = "查询专项附加扣除---带分页")
  public Result<Page<AddSpecialVo>> findAddSpecialPage(@RequestBody AddSpecialQueryDto addSpecialQueryDto){
    return success(addSpecialService.findPage(addSpecialQueryDto));
  }

  @GetMapping("/deleteAddSpecial/{id}")
  @ApiOperation(value = "删除专项附加扣除")
  public Result deleteAddSpecial(@PathVariable Long id){
    addSpecialService.delete(id);
    return success();
  }

  @PostMapping("findOtherReduce")
  @ApiOperation(value = "分页查询其他扣除")
  public Result<Page<OtherReduceVo>> findOtherReduce(@RequestBody OtherReduceQuery query) {
    return success(otherReduceService.findByPage(query));
  }

  @PostMapping("getOtherReduce")
  @ApiOperation("获取所有其他扣除")
  public Result<List<OtherReduce>> getOtherReduce() {
    return success(otherReduceService.getOtherReduce());
  }

  @PostMapping("saveOtherReduce")
  @ApiOperation(value = "其他扣除添加")
  public Result<OtherReduce> saveOtherReduce(@RequestBody OtherReduceForm form) {
    return success(otherReduceService.save(form));
  }

  @GetMapping("deleteOtherReduce/{id}")
  @ApiOperation(value = "根据id删除其他扣除")
  public Result deleteOtherReduce(@PathVariable Long id) {
    otherReduceService.delete(id);
    return success();
  }

  @GetMapping("exportReportData")
  @ApiOperation("导出所有申报数据的excel模块")
  public void exportReportData(){

  }
}
