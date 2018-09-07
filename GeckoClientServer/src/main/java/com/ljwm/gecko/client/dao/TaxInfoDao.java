package com.ljwm.gecko.client.dao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.vo.TaxIncomeVo;
import com.ljwm.gecko.base.model.vo.TaxOtherReduceVo;
import com.ljwm.gecko.base.model.vo.TaxSpecialAddVo;
import com.ljwm.gecko.base.model.vo.TaxSpecialVo;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.model.dto.TaxIncomeForm;
import com.ljwm.gecko.client.model.dto.TaxOtherReduceForm;
import com.ljwm.gecko.client.model.dto.TaxSpecialAddForm;
import com.ljwm.gecko.client.model.dto.TaxSpecialForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/5 17:41
 */
@Repository
public class TaxInfoDao {

  Long currentId = SecurityKit.currentId();

  @Autowired
  ApplicationInfo appInfo;

  @Autowired
  TaxMapper taxMapper;

  @Autowired
  TaxIncomeMapper taxIncomeMapper;

  @Autowired
  TaxOtherReduceMapper taxOtherReduceMapper;

  @Autowired
  TaxSpecialMapper taxSpecialMapper;

  @Autowired
  TaxSpecialAddMapper taxSpecialAddMapper;

  public Tax insertOrUpdateTax(Tax tax) {
    Map<String, Object> map = new HashMap<>();
    map.put("MEMBER_ID", tax.getMemberId());
    map.put("DECLARE_TYPE", tax.getDeclareType());
    map.put("DECLARE_TIME", tax.getDeclareTime());
    List<Tax> list = taxMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      tax = list.get(0);
      tax.setUpdateTime(new Date());
      taxMapper.updateById(tax);
    } else {
      Date date = new Date();
      tax.setCreateTime(date).setUpdateTime(date);
      taxMapper.insert(tax);
    }
    return tax;
  }

  public void insertOrUpdateTaxIncome(TaxIncomeForm taxIncomeForm, Long taxId) {
    Map<String, Object> map = new HashMap<>();
    map.put("TAX_ID", taxId);
    map.put("INCOME_TYPE_ID", taxIncomeForm.getIncomeTypeId());
    List<TaxIncome> list = taxIncomeMapper.selectByMap(map);
    TaxIncome taxIncome = new TaxIncome();
    if(CollectionUtil.isNotEmpty(list)){
      taxIncome = list.get(0);
      BeanUtil.copyProperties(taxIncomeForm, taxIncome);
      taxIncome.setUpdateTime(new Date());
      taxIncome.setUpdater(currentId);
      taxIncomeMapper.updateById(taxIncome);
    } else {
      Date date = new Date();
      BeanUtil.copyProperties(taxIncomeForm, taxIncome);
      taxIncome.setTaxId(taxId).setCreateTime(date).setUpdateTime(date);
      taxIncomeMapper.insert(taxIncome);
    }
  }

  public void insertOrUpdateTaxOther(TaxOtherReduceForm taxOtherReduceForm, Long taxId, Long memberId) {
    Map<String, Object> map = new HashMap<>();
    map.put("TAX_ID", taxId);
    map.put("OTHER_REDUCE_ID", taxOtherReduceForm.getOtherReduceId());
    List<TaxOtherReduce> list = taxOtherReduceMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      TaxOtherReduce taxOtherReduce = list.get(0);
      taxOtherReduce.setUpdateTime(new Date());
      taxOtherReduce.setUpdater(currentId);
      if (StrUtil.isNotBlank(taxOtherReduceForm.getTaxDocPath()) && taxOtherReduceForm.getTaxDocPath().indexOf(Constant.HTTP) == -1) {
        String destDir = appInfo.getFilePath() + Constant.TAX + memberId + "/";
        String srcPath = appInfo.getFilePath()+Constant.CACHE + taxOtherReduceForm.getTaxDocPath();
        Fileutil.cutGeneralFile(srcPath, destDir);
        taxOtherReduce.setTaxDocPath(Constant.TAX+ memberId + "/" + taxOtherReduceForm.getTaxDocPath());
      }
      taxOtherReduceMapper.updateById(taxOtherReduce);
    } else {
      Date date = new Date();
      TaxOtherReduce taxOtherReduce = new TaxOtherReduce();
      BeanUtil.copyProperties(taxOtherReduceForm, taxOtherReduce);
      if (StrUtil.isNotBlank(taxOtherReduceForm.getTaxDocPath()) && taxOtherReduceForm.getTaxDocPath().indexOf(Constant.HTTP) == -1) {
        String destDir = appInfo.getFilePath() + Constant.TAX + memberId + "/";
        File file = new File(destDir);
        if(!file.exists()){
          file.mkdirs();
        }
        String srcPath = appInfo.getFilePath()+Constant.CACHE + taxOtherReduceForm.getTaxDocPath();
        Fileutil.cutGeneralFile(srcPath, destDir);
        taxOtherReduce.setTaxDocPath(Constant.TAX+ memberId + "/" + taxOtherReduceForm.getTaxDocPath());
      }
      taxOtherReduce.setTaxId(taxId).setCreateTime(date).setUpdateTime(date);
      taxOtherReduceMapper.insert(taxOtherReduce);
    }
  }

  public void insertOrUpdateTaxSpecialAdd(TaxSpecialAddForm taxSpecialAddForm, Long taxId) {
    Map<String, Object> map = new HashMap<>();
    map.put("TAX_ID", taxId);
    map.put("SPECIAL_ADD_ID", taxSpecialAddForm.getSpecialAddId());
    List<TaxSpecialAdd> list = taxSpecialAddMapper.selectByMap(map);
    TaxSpecialAdd taxSpecialAdd = new TaxSpecialAdd();
    if(CollectionUtil.isNotEmpty(list)){
      taxSpecialAdd = list.get(0);
      BeanUtil.copyProperties(taxSpecialAddForm, taxSpecialAdd);
      taxSpecialAdd.setUpdateTime(new Date()).setUpdater(currentId);
      taxSpecialAddMapper.updateById(taxSpecialAdd);
    }else {
      Date date = new Date();
      BeanUtil.copyProperties(taxSpecialAddForm, taxSpecialAdd);
      taxSpecialAdd.setTaxId(taxId).setCreateTime(date).setUpdateTime(date);
      taxSpecialAddMapper.insert(taxSpecialAdd);
    }
  }

  public void insertOrUpdateTaxSpecial(TaxSpecialForm taxSpecialForm, Long taxId) {
    Map<String, Object> map = new HashMap<>();
    map.put("TAX_ID", taxId);
    map.put("SPECIAL_DEDU_ID", taxSpecialForm.getSpecialDeduId());
    List<TaxSpecial> list = taxSpecialMapper.selectByMap(map);
    TaxSpecial taxSpecial = new TaxSpecial();
    if(CollectionUtil.isNotEmpty(list)){
      taxSpecial = list.get(0);
      BeanUtil.copyProperties(taxSpecialForm, taxSpecial);
      taxSpecial.setUpdateTime(new Date()).setUpdater(currentId);
      taxSpecialMapper.updateById(taxSpecial);
    } else {
      Date date = new Date();
      BeanUtil.copyProperties(taxSpecialForm, taxSpecial);
      taxSpecial.setTaxId(taxId).setCreateTime(date).setUpdateTime(date);
      taxSpecialMapper.insert(taxSpecial);
    }
  }

  public List<TaxIncomeVo> selectIncomeInfo(Long memberId, String declareTime, Integer declareType) {
    List<TaxIncomeVo> list = taxIncomeMapper.selectIncome(memberId, declareTime, declareType);
    if(CollectionUtil.isNotEmpty(list)) {
      return list;
    }
    return null;
  }

  public List<TaxOtherReduceVo> selectOther(Long memberId, String declareTime, Integer declareType) {
    List<TaxOtherReduceVo> list = taxOtherReduceMapper.selectOther(memberId, declareTime, declareType);
    if(CollectionUtil.isNotEmpty(list)) {
      for(TaxOtherReduceVo taxOtherReduceVo: list){
        if(StrUtil.isNotBlank(taxOtherReduceVo.getTaxDocPath())) {
          taxOtherReduceVo.setTaxDocPath(appInfo.getWebPath() + taxOtherReduceVo.getTaxDocPath());
        }
      }
      return list;
    }
    return null;
  }

  public List<TaxSpecialVo> selectSpecial(Long memberId, String declareTime, Integer declareType) {
    List<TaxSpecialVo> list = taxSpecialMapper.selectSpecial(memberId, declareTime, declareType);
    if(CollectionUtil.isNotEmpty(list)) {
      return list;
    }
    return null;
  }

  public List<TaxSpecialAddVo> selectSpecialAdd(Long memberId, String declareTime, Integer declareType) {
    List<TaxSpecialAddVo> list = taxSpecialAddMapper.selectSpecialAdd(memberId, declareTime, declareType);
    if(CollectionUtil.isNotEmpty(list)) {
      for(TaxSpecialAddVo taxSpecialAddVo: list){
        if(StrUtil.isNotBlank(taxSpecialAddVo.getTaxDocPath())) {
          taxSpecialAddVo.setTaxDocPath(appInfo.getWebPath() + taxSpecialAddVo.getTaxDocPath());
        }
      }
      return list;
    }
    return null;
  }
}
