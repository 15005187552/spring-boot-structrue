package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.Tax;
import com.ljwm.gecko.base.model.vo.TaxIncomeVo;
import com.ljwm.gecko.base.model.vo.TaxOtherReduceVo;
import com.ljwm.gecko.base.model.vo.TaxSpecialAddVo;
import com.ljwm.gecko.base.model.vo.TaxSpecialVo;
import com.ljwm.gecko.client.dao.TaxInfoDao;
import com.ljwm.gecko.client.model.dto.*;
import com.ljwm.gecko.client.model.vo.TaxVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/5 17:36
 */
@Service
public class TaxDeclarationService {
  @Autowired
  TaxInfoDao taxInfoDao;

  public Result commit(TaxForm taxForm) {
    Tax tax = new Tax();
    BeanUtil.copyProperties(taxForm, tax);
    tax = taxInfoDao.insertOrUpdateTax(tax);
    Long taxId = tax.getId();
    Long memberId = tax.getMemberId();
    List<TaxIncomeForm> taxIncomeFormList = taxForm.getTaxIncomeFormList();
    List<TaxOtherReduceForm> taxOtherReduceFormList = taxForm.getTaxOtherReduceFormList();
    List<TaxSpecialForm> taxSpecialList = taxForm.getTaxSpecialFormList();
    List<TaxSpecialAddForm> taxSpecialAddFormList = taxForm.getTaxSpecialAddFormList();
    if(CollectionUtil.isNotEmpty(taxIncomeFormList)){
      for (TaxIncomeForm taxIncomeForm :taxIncomeFormList){
        taxInfoDao.insertOrUpdateTaxIncome(taxIncomeForm, taxId);
      }
    }
    if(CollectionUtil.isNotEmpty(taxOtherReduceFormList)){
      for (TaxOtherReduceForm taxOtherReduceForm :taxOtherReduceFormList){
        taxInfoDao.insertOrUpdateTaxOther(taxOtherReduceForm, taxId, memberId);
      }
    }
    if(CollectionUtil.isNotEmpty(taxSpecialList)){
      for (TaxSpecialForm taxSpecialForm :taxSpecialList){
        taxInfoDao.insertOrUpdateTaxSpecial(taxSpecialForm, taxId);
      }
    }
    if(CollectionUtil.isNotEmpty(taxSpecialAddFormList)){
      for (TaxSpecialAddForm taxSpecialAddForm :taxSpecialAddFormList){
        taxInfoDao.insertOrUpdateTaxSpecialAdd(taxSpecialAddForm, taxId);
      }
    }
    return Result.success("提交成功!");
  }

  public Result find(TaxInfoForm taxInfoForm) {
    Long memberId = taxInfoForm.getMemberId();
    String declareTime = taxInfoForm.getDeclareTime();
    Integer declareType = taxInfoForm.getDeclareType();
    List<TaxIncomeVo> incomeVoList = taxInfoDao.selectIncomeInfo(memberId, declareTime ,declareType);
    List<TaxOtherReduceVo> taxOtherReduceList = taxInfoDao.selectOther(memberId, declareTime ,declareType);
    List<TaxSpecialVo> taxSpecialVoList = taxInfoDao.selectSpecial(memberId, declareTime ,declareType);
    List<TaxSpecialAddVo> taxSpecialAddVoList = taxInfoDao.selectSpecialAdd(memberId, declareTime ,declareType);
    if(CollectionUtil.isNotEmpty(incomeVoList)||CollectionUtil.isNotEmpty(taxOtherReduceList)||
      CollectionUtil.isNotEmpty(taxSpecialVoList)||CollectionUtil.isNotEmpty(taxSpecialAddVoList)){
      TaxVo taxVo = new TaxVo();
      taxVo.setIncomeVoList(incomeVoList).setOtherReduceVoList(taxOtherReduceList)
      .setSpecialVoList(taxSpecialVoList).setSpecialAddVoList(taxSpecialAddVoList);
      return Result.success(taxVo);
    }
    return  Result.success(null);
  }
}
