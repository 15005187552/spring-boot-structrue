package com.ljwm.gecko.base.service;

import com.ljwm.gecko.base.model.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReportDataService {

  @Autowired
  private IncomeTypeService incomeTypeService;

  @Autowired
  private AddSpecialService addSpecialService;

  @Autowired
  private OtherReduceService otherReduceService;

  @Autowired
  private SpecialDeductionService specialDeductionService;

  public ReportDataVo findReportData(){
    ReportDataVo reportDataVo = new ReportDataVo();
    List<IncomeTypeVo> incomeTypeVoList = incomeTypeService.find();
    if (CollectionUtils.isNotEmpty(incomeTypeVoList)){
      reportDataVo.setIncomeTypeVoList(incomeTypeVoList);
    }
    List<AddSpecialVo> addSpecialVoList = addSpecialService.find();
    if (CollectionUtils.isNotEmpty(addSpecialVoList)){
      reportDataVo.setAddSpecialVoList(addSpecialVoList);
    }
    List<OtherReduceVo> otherReduceVoList = otherReduceService.find();
    if (CollectionUtils.isNotEmpty(otherReduceVoList)){
      reportDataVo.setOtherReduceVoList(otherReduceVoList);
    }
    List<SpecialDeductionVo> specialDeductionVoList = specialDeductionService.find();
    if (CollectionUtils.isNotEmpty(specialDeductionVoList)){
      reportDataVo.setSpecialDeductionVoList(specialDeductionVoList);
    }
    return reportDataVo;
  }
}
