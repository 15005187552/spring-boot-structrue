package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.Tax;
import com.ljwm.gecko.base.mapper.TaxMapper;
import com.ljwm.gecko.base.model.vo.*;
import com.ljwm.gecko.client.dao.TaxInfoDao;
import com.ljwm.gecko.client.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/5 17:36
 */
@Service
public class TaxDeclarationService {
  @Autowired
  TaxInfoDao taxInfoDao;

  @Autowired
  CommonService commonService;

  @Autowired
  TaxMapper taxMapper;

  @Transactional
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

  public TaxVo find(TaxInfoForm taxInfoForm) {
    Long memberId = taxInfoForm.getMemberId();
    String declareTime = taxInfoForm.getDeclareTime();
    Integer declareType = taxInfoForm.getDeclareType();
    Tax tax = taxMapper.selectOne(new QueryWrapper<Tax>()
        .eq(Tax.MEMBER_ID, memberId).eq(Tax.DECLARE_TIME, declareTime).eq(Tax.DECLARE_TYPE, declareType));
    List<TaxIncomeVo> incomeVoList = taxInfoDao.selectIncomeInfo(memberId, declareTime ,declareType);
    List<TaxOtherReduceVo> taxOtherReduceList = taxInfoDao.selectOther(memberId, declareTime ,declareType);
    List<TaxSpecialVo> taxSpecialVoList = taxInfoDao.selectSpecial(memberId, declareTime ,declareType);
    List<TaxSpecialAddVo> taxSpecialAddVoList = taxInfoDao.selectSpecialAdd(memberId, declareTime ,declareType);
    if(CollectionUtil.isNotEmpty(incomeVoList)||CollectionUtil.isNotEmpty(taxOtherReduceList)||
      CollectionUtil.isNotEmpty(taxSpecialVoList)||CollectionUtil.isNotEmpty(taxSpecialAddVoList)){
      TaxVo taxVo = new TaxVo();
      BeanUtil.copyProperties(tax, taxVo);
      taxVo.setIncomeVoList(incomeVoList).setOtherReduceVoList(taxOtherReduceList)
      .setSpecialVoList(taxSpecialVoList).setSpecialAddVoList(taxSpecialAddVoList);
      return taxVo;
    }
    return  null;
  }

  public Result declareType(RecordForm recordForm) {
    Map<String, Object> map = new HashMap<>();
    map.put("memberId", SecurityKit.currentId());
    map.put("declareType", recordForm.getDeclareType());
    return Result.success(commonService.find(recordForm, (p, q) -> taxInfoDao.selectByPage(p, map)));
  }

  public Result findTaxList(TaxListForm taxListForm) {
    Page<NaturalPersonTaxVo> page = commonService.find(taxListForm, (p, q) -> taxInfoDao.selectTaxByList(p, BeanUtil.beanToMap(taxListForm)));
    List<NaturalPersonTaxVo> list = page.getRecords();
    for(NaturalPersonTaxVo naturalPersonTaxVo:list){
      TaxVo taxVo = naturalPersonTaxVo.getTaxVo();
      TaxInfoForm taxInfoForm = new TaxInfoForm().setMemberId(taxVo.getMemberId()).setDeclareTime(taxVo.getDeclareTime()).setDeclareType(taxVo.getDeclareType());
      naturalPersonTaxVo.setTaxVo(find(taxInfoForm));
    }
    return Result.success(page);
  }

  public Result findTaxById(Long taxId) {
    List<NaturalPersonTaxVo> list = taxMapper.selectTaxInfo(taxId);
    if(CollectionUtil.isNotEmpty(list)){
      NaturalPersonTaxVo naturalPersonTaxVo = list.get(0);
      TaxVo taxVo = naturalPersonTaxVo.getTaxVo();
      TaxInfoForm taxInfoForm = new TaxInfoForm().setMemberId(taxVo.getMemberId()).setDeclareTime(taxVo.getDeclareTime()).setDeclareType(taxVo.getDeclareType());
      naturalPersonTaxVo.setTaxVo(find(taxInfoForm));
      return Result.success(naturalPersonTaxVo);
    }
    return Result.success(null);
  }
}
