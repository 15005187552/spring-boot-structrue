package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.Tax;
import com.ljwm.gecko.client.dao.TaxInfoDao;
import com.ljwm.gecko.client.model.dto.*;
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
}
