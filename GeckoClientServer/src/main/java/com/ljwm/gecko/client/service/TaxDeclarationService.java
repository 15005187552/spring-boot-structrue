package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.vo.*;
import com.ljwm.gecko.client.dao.TaxInfoDao;
import com.ljwm.gecko.client.model.dto.*;
import com.ljwm.gecko.client.model.vo.TaxResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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

  @Autowired
  CalcService calcService;

  @Autowired
  TaxIncomeMapper taxIncomeMapper;

  @Autowired
  TaxSpecialMapper taxSpecialMapper;

  @Autowired
  TaxSpecialAddMapper taxSpecialAddMapper;

  @Autowired
  TaxOtherReduceMapper taxOtherReduceMapper;

  @Autowired
  AttendanceMapper attendanceMapper;

  @Autowired
  NaturalPersonMapper naturalPersonMapper;

  @Autowired
  IncomeTypeMapper incomeTypeMapper;

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
    BigDecimal otherReduce = BigDecimal.ZERO;
    if(CollectionUtil.isNotEmpty(taxIncomeFormList)){
      for (TaxIncomeForm taxIncomeForm :taxIncomeFormList){
        taxInfoDao.insertOrUpdateTaxIncome(taxIncomeForm, taxId);
      }
    }
    if(CollectionUtil.isNotEmpty(taxOtherReduceFormList)){
      for (TaxOtherReduceForm taxOtherReduceForm :taxOtherReduceFormList){
        otherReduce = otherReduce.add(taxOtherReduceForm.getTaxMoney());
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
    BigDecimal income = BigDecimal.ZERO;
    for(TaxIncomeForm taxIncomeForm : taxIncomeFormList){
      IncomeType incomeType = incomeTypeMapper.selectOne(new QueryWrapper<IncomeType>().like(IncomeType.NAME, "综合所得"));
      if(incomeType!= null && incomeType.getId().equals(taxIncomeForm.getPId())) {
        income = income.add(new BigDecimal(taxIncomeForm.getIncome()));
      }
    }
    BigDecimal specialDe = BigDecimal.ZERO;
    for (TaxSpecialForm taxSpecialForm : taxSpecialList){
      specialDe = specialDe.add(new BigDecimal(taxSpecialForm.getPersonalMoney()));
    }
    BigDecimal money = income.subtract(specialDe);
    BigDecimal newTax = calcService.calNew(money, new BigDecimal("5000"));

    TaxResultVo taxResultVo = new TaxResultVo()
      .setSocialFee(specialDe).setTax(newTax).setAfterTax(money.subtract(newTax).subtract(otherReduce)).setIncomeAdvice("收入建议").setDeducAdvice("扣除建议");
    tax.setBeforeTax(money.subtract(newTax).subtract(otherReduce)).setBeforeTax(money);
    taxMapper.updateById(tax);
    return Result.success(taxResultVo);
  }

  public TaxVo find(TaxInfoForm taxInfoForm) {
    Long memberId = taxInfoForm.getMemberId();
    String declareTime = taxInfoForm.getDeclareTime();
    Integer declareType = taxInfoForm.getDeclareType();
    Tax tax = taxMapper.selectOne(new QueryWrapper<Tax>()
        .eq(Tax.MEMBER_ID, memberId).eq(Tax.DECLARE_TIME, declareTime).eq(Tax.DECLARE_TYPE, declareType));
    List<com.ljwm.gecko.base.model.vo.TaxIncomeVo> incomeVoList = taxInfoDao.selectIncomeInfo(memberId, declareTime ,declareType);
    List<com.ljwm.gecko.base.model.vo.TaxOtherReduceVo> taxOtherReduceList = taxInfoDao.selectOther(memberId, declareTime ,declareType);
    List<com.ljwm.gecko.base.model.vo.TaxSpecialVo> taxSpecialVoList = taxInfoDao.selectSpecial(memberId, declareTime ,declareType);
    List<com.ljwm.gecko.base.model.vo.TaxSpecialAddVo> taxSpecialAddVoList = taxInfoDao.selectSpecialAdd(memberId, declareTime ,declareType);
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

  public NaturalPersonTaxVo findTaxById(Long taxId) {
    List<NaturalPersonTaxVo> list = taxMapper.selectTaxInfo(taxId);
    if(CollectionUtil.isNotEmpty(list)){
      NaturalPersonTaxVo naturalPersonTaxVo = list.get(0);
      TaxVo taxVo = naturalPersonTaxVo.getTaxVo();
      TaxInfoForm taxInfoForm = new TaxInfoForm().setMemberId(taxVo.getMemberId()).setDeclareTime(taxVo.getDeclareTime()).setDeclareType(taxVo.getDeclareType());
      naturalPersonTaxVo.setTaxVo(find(taxInfoForm));
      return naturalPersonTaxVo;
    }
    return null;
  }

  public AttendanceTaxVo findTaxInfo(Long taxId) {
    AttendanceTaxVo attendanceTaxVo = new AttendanceTaxVo();
    List<Attendance> attendanceList = attendanceMapper.selectList(new QueryWrapper<Attendance>().eq(Attendance.TAX_ID, taxId));
    List<TaxIncome> taxIncomeList = taxIncomeMapper.selectList(new QueryWrapper<TaxIncome>().eq(TaxIncome.TAX_ID, taxId));
    List<TaxSpecial> taxSpecialList = taxSpecialMapper.selectList(new QueryWrapper<TaxSpecial>().eq(TaxSpecial.TAX_ID, taxId));
    List<TaxSpecialAdd> taxSpecialAddList = taxSpecialAddMapper.selectList(new QueryWrapper<TaxSpecialAdd>().eq(TaxSpecialAdd.TAX_ID, taxId));
    List<TaxOtherReduce> taxOtherReduceList = taxOtherReduceMapper.selectList(new QueryWrapper<TaxOtherReduce>().eq(TaxOtherReduce.TAX_ID, taxId));
    if(CollectionUtil.isNotEmpty(attendanceList)){
      List<AttendanceVo> attendanceVoList = new ArrayList<>();
      for (Attendance attendance: attendanceList){
        AttendanceVo attendanceVo = new AttendanceVo();
        BeanUtil.copyProperties(attendance, attendanceVo);
        attendanceVoList.add(attendanceVo);
      }
      attendanceTaxVo.setAttendanceList(attendanceVoList);
    }
    if(CollectionUtil.isNotEmpty(taxIncomeList)){
      List<TaxIncomeVo> taxIncomeVoList = new ArrayList<>();
      for (TaxIncome taxIncome: taxIncomeList){
        TaxIncomeVo taxIncomeVo = new TaxIncomeVo();
        BeanUtil.copyProperties(taxIncome, taxIncomeVo);
        taxIncomeVoList.add(taxIncomeVo);
      }
      attendanceTaxVo.setIncomeList(taxIncomeVoList);
    }
    if(CollectionUtil.isNotEmpty(taxSpecialList)){
      List<TaxSpecialVo> taxSpecialVoList = new ArrayList<>();
      for (TaxSpecial taxSpecial: taxSpecialList){
        TaxSpecialVo taxSpecialVo = new TaxSpecialVo();
        BeanUtil.copyProperties(taxSpecial, taxSpecialVo);
        taxSpecialVoList.add(taxSpecialVo);
      }
      attendanceTaxVo.setSpecialList(taxSpecialVoList);
    }
    if(CollectionUtil.isNotEmpty(taxSpecialAddList)){
      List<TaxSpecialAddVo> taxSpecialAddVoList = new ArrayList<>();
      for (TaxSpecialAdd taxSpecialAdd: taxSpecialAddList){
        TaxSpecialAddVo taxSpecialAddVo = new TaxSpecialAddVo();
        BeanUtil.copyProperties(taxSpecialAdd, taxSpecialAddVo);
        taxSpecialAddVoList.add(taxSpecialAddVo);
      }
      attendanceTaxVo.setSpecialAddList(taxSpecialAddVoList);
    }
    if(CollectionUtil.isNotEmpty(taxOtherReduceList)){
      List<TaxOtherReduceVo> taxOtherReduceVoList = new ArrayList<>();
      for (TaxOtherReduce taxOtherReduce: taxOtherReduceList){
        TaxOtherReduceVo taxOtherReduceVo = new TaxOtherReduceVo();
        BeanUtil.copyProperties(taxOtherReduce, taxOtherReduceVo);
        taxOtherReduceVoList.add(taxOtherReduceVo);
      }
      attendanceTaxVo.setOtherReduceList(taxOtherReduceVoList);
    }
    return attendanceTaxVo;
  }

  public Result findListByCompanyId(TaxFindForm taxFindForm) {
    Page<Tax> page = commonService.find(taxFindForm, (p, q) -> taxMapper.selectTaxList(p, BeanUtil.beanToMap(taxFindForm)));
    return Result.success(page);
  }
}
