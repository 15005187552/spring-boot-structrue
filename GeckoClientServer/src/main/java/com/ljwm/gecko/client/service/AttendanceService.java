package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.enums.CertificateType;
import com.ljwm.gecko.base.enums.TableNameEnum;
import com.ljwm.gecko.base.enums.TaxStatus;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.vo.*;
import com.ljwm.gecko.base.utils.EnumUtil;
import com.ljwm.gecko.client.model.dto.AttendanceForm;
import com.ljwm.gecko.client.model.dto.TaxFindForm;
import com.ljwm.gecko.client.model.vo.AttendanceData;
import com.ljwm.gecko.client.model.vo.AttendanceTaxVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/17 15:06
 */
@Service
public class AttendanceService {

  @Autowired
  NaturalPersonMapper naturalPersonMapper;

  @Autowired
  TaxMapper taxMapper;

  @Autowired
  AttributeMapper attributeMapper;

  @Autowired
  TaxIncomeMapper taxIncomeMapper;

  @Autowired
  TaxOtherReduceMapper taxOtherReduceMapper;

  @Autowired
  TaxSpecialAddMapper taxSpecialAddMapper;

  @Autowired
  TaxSpecialMapper taxSpecialMapper;

  @Autowired
  AttendanceMapper attendanceMapper;

  @Autowired
  CompanySpecialMapper companySpecialMapper;

  @Autowired
  SpecialDeductionMapper specialDeductionMapper;

  @Autowired
  CommonService commonService;

  @Autowired
  TaxDeclarationService taxDeclarationService;

  @Autowired
  CompanyUserMapper companyUserMapper;

  @Autowired
  CompanyUserInfoMapper companyUserInfoMapper;

  @Autowired
  ExcelService excelService;

  @Transactional
  public Result commit(AttendanceForm attendanceForm) {
    Long companyId = attendanceForm.getCompanyId();
    String declareTime = attendanceForm.getDeclareTime();
    List<AttendanceForm.AttendanceDto> list = attendanceForm.getList();
    for (AttendanceForm.AttendanceDto attendanceDto : list){
      BigDecimal socialBase = new BigDecimal(attendanceDto.getSocialBase());
      BigDecimal fundBase = new BigDecimal(attendanceDto.getFundBase());
      BigDecimal fundPer = new BigDecimal(attendanceDto.getFundPer());
      Integer certificate = EnumUtil.getEnumByName(CertificateType.class, attendanceDto.getCertificate()).getCode();
      String idCard = attendanceDto.getIdCard();
      NaturalPerson naturalPerson = naturalPersonMapper.selectOne(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.CERT_NUM, idCard).eq(NaturalPerson.CERTIFICATE, certificate));
      if (naturalPerson != null) {
        Long memberId = naturalPerson.getMemberId();
        CompanyUser companyUser = companyUserMapper.selectOne(new QueryWrapper<CompanyUser>().eq(CompanyUser.COMPANY_ID, companyId).eq(CompanyUser.MEMBER_ID, memberId));
        CompanyUserInfo companyUserInfo = companyUserInfoMapper.selectById(companyUser.getId());
        if(companyUserInfo != null){
          companyUserInfo.setFundBase(fundBase).setFundPer(fundPer).setSocialBase(socialBase);
          companyUserInfoMapper.updateById(companyUserInfo);
        }
        Tax tax = taxMapper.selectOne(new QueryWrapper<Tax>().eq(Tax.DECLARE_TIME, declareTime).eq(Tax.MEMBER_ID, memberId).eq(Tax.DECLARE_TYPE, attendanceForm.getDeclareType()));
        Date date = new Date();
        if (tax != null){
          tax.setUpdateTime(date).setStatus(TaxStatus.NEED_CONFIRM.getCode());
          taxMapper.updateById(tax);
        } else {
          tax = new Tax().setUpdateTime(date).setDeclareType(attendanceForm.getDeclareType()).setDeclareTime(declareTime).setMemberId(memberId).setStatus(TaxStatus.NEED_CONFIRM.getCode());
          tax.setCreateTime(date);
          taxMapper.insert(tax);
        }
        insertOrUpdate(socialBase, fundBase, fundPer, companyId, date, tax);
        List<AttendanceForm.AttendanceDto.AttendanceData> dataList = attendanceDto.getDataList();
        for (AttendanceForm.AttendanceDto.AttendanceData attendanceData : dataList){
          Attribute attribute = attributeMapper.selectById(attendanceData.getId());
          Integer tableName = attribute.getTableName();
          Long itemId = attribute.getItemId();
          String value = attendanceData.getValue();
          insertOrUpdate(tableName, itemId, date, value, tax);
        }
      }else {
        return Result.fail("证件号码或者证照类型有误！");
      }
    }
    return Result.success("成功！");
  }


  public void insertOrUpdate(Integer tableName, Long itemId, Date date, String value, Tax tax){
    if(tableName == TableNameEnum.T_INCOME_TYPE.getCode()){
      TaxIncome taxIncome = taxIncomeMapper.selectOne(new QueryWrapper<TaxIncome>().eq(TaxIncome.TAX_ID, tax.getId())
        .eq(TaxIncome.INCOME_TYPE_ID, itemId));
      if (taxIncome != null){
        taxIncome.setUpdateTime(date).setIncome(new BigDecimal(value)).setUpdater(SecurityKit.currentId()).setIncomeTypeId(itemId).setTaxId(tax.getId());
        taxIncomeMapper.updateById(taxIncome);
      } else {
        taxIncome = new TaxIncome();
        taxIncome.setUpdateTime(date).setIncome(new BigDecimal(value)).setUpdater(SecurityKit.currentId()).setCreateTime(date).setIncomeTypeId(itemId).setTaxId(tax.getId());
        taxIncomeMapper.insert(taxIncome);
      }
    }
    if(tableName == TableNameEnum.T_OTHER_REDUCE.getCode()){
      TaxOtherReduce taxOtherReduce = taxOtherReduceMapper.selectOne(new QueryWrapper<TaxOtherReduce>()
        .eq(TaxOtherReduce.TAX_ID, tax.getId()).eq(TaxOtherReduce.OTHER_REDUCE_ID, itemId));
      if (taxOtherReduce != null){
        taxOtherReduce.setUpdateTime(date).setTaxMoney(new BigDecimal(value)).setUpdater(SecurityKit.currentId()).setOtherReduceId(itemId).setTaxId(tax.getId());
        taxOtherReduceMapper.updateById(taxOtherReduce);
      } else {
        taxOtherReduce = new TaxOtherReduce();
        taxOtherReduce.setUpdateTime(date).setTaxMoney(new BigDecimal(value)).setUpdater(SecurityKit.currentId()).setCreateTime(date).setOtherReduceId(itemId).setTaxId(tax.getId());
        taxOtherReduceMapper.insert(taxOtherReduce);
      }
    }
    /*List<CompanySpecial> companySpecialList = companySpecialMapper.selectList(new QueryWrapper<CompanySpecial>().eq(CompanySpecial.COMPANY_ID, companyId));
    for (CompanySpecial companySpecial : companySpecialList){
      SpecialDeduction specialDeduction = specialDeductionMapper.selectOne(new QueryWrapper<SpecialDeduction>().like(SpecialDeduction.NAME, "公积金"));
      TaxSpecial taxSpecial = taxSpecialMapper.selectOne(new QueryWrapper<TaxSpecial>().eq(TaxSpecial.TAX_ID, tax.getId()).eq(TaxSpecial.SPECIAL_DEDU_ID, companySpecial.getSpecialId()));
      if (specialDeduction.getId().equals(companySpecial.getSpecialId())){
        if(taxSpecial != null) {
          taxSpecial.setUpdater(SecurityKit.currentId()).setUpdateTime(date).setCompanyMoney(fundBase.multiply(fundPer))
            .setPersonalMoney(fundBase.multiply(fundPer)).setCompanyPercent(fundPer).setPersonalPercent(fundPer);
          taxSpecialMapper.updateById(taxSpecial);
        } else {
          taxSpecial = new TaxSpecial();
          taxSpecial.setUpdater(SecurityKit.currentId()).setUpdateTime(date).setCompanyMoney(fundBase.multiply(fundPer))
            .setPersonalMoney(fundBase.multiply(fundPer)).setCompanyPercent(fundPer).setPersonalPercent(fundPer)
            .setTaxId(tax.getId()).setSpecialDeduId(companySpecial.getSpecialId());
          taxSpecialMapper.updateById(taxSpecial);
        }
      } else {
        if (taxSpecial != null) {
          BigDecimal companyPer = companySpecial.getCompanyPer();
          BigDecimal personPer = companySpecial.getPersonPer();
          taxSpecial.setUpdateTime(date).setUpdater(SecurityKit.currentId()).setCompanyMoney(socialBase.multiply(companyPer))
            .setPersonalMoney(socialBase.multiply(personPer)).setCompanyPercent(companyPer).setPersonalPercent(personPer);
          taxSpecialMapper.updateById(taxSpecial);
        } else {
          BigDecimal companyPer = companySpecial.getCompanyPer();
          BigDecimal personPer = companySpecial.getPersonPer();
          taxSpecial = new TaxSpecial();
          taxSpecial.setUpdateTime(date).setUpdater(SecurityKit.currentId()).setCompanyMoney(socialBase.multiply(companyPer))
            .setPersonalMoney(socialBase.multiply(personPer)).setCompanyPercent(companyPer).setPersonalPercent(personPer)
            .setTaxId(tax.getId()).setSpecialDeduId(companySpecial.getSpecialId());
          taxSpecialMapper.insert(taxSpecial);
        }
      }
    }*/
    if(tableName == TableNameEnum.T_ADD_SPECIAL.getCode()){
      TaxSpecialAdd taxSpecialAdd = taxSpecialAddMapper.selectOne(new QueryWrapper<TaxSpecialAdd>()
        .eq(TaxSpecialAdd.TAX_ID, tax.getId()).eq(TaxSpecialAdd.SPECIAL_ADD_ID, itemId));
      if (taxSpecialAdd != null){
        taxSpecialAdd.setUpdateTime(date).setTaxMoney(new BigDecimal(value)).setUpdater(SecurityKit.currentId()).setSpecialAddId(itemId);
        taxSpecialAddMapper.updateById(taxSpecialAdd);
      } else {
        taxSpecialAdd = new TaxSpecialAdd();
        taxSpecialAdd.setUpdateTime(date).setTaxMoney(new BigDecimal(value)).setUpdater(SecurityKit.currentId()).setCreateTime(date).setSpecialAddId(itemId);
        taxSpecialAddMapper.insert(taxSpecialAdd);
      }
    }
    if(tableName == TableNameEnum.T_ATTENDANCE.getCode()){
      Attendance attendance = attendanceMapper.selectOne(new QueryWrapper<Attendance>()
        .eq(Attendance.TAX_ID, tax.getId()).eq(Attendance.ATTRIBUTE_ID, itemId));
      if (attendance != null){
        attendance.setValue(value);
        attendanceMapper.updateById(attendance);
      } else {
        attendance = new Attendance();
        attendance.setValue(value);
        attendanceMapper.insert(attendance);
      }
    }
  }

  public void insertOrUpdate(BigDecimal socialBase, BigDecimal fundBase, BigDecimal fundPer, Long companyId, Date date, Tax tax){
    List<CompanySpecial> companySpecialList = companySpecialMapper.selectList(new QueryWrapper<CompanySpecial>().eq(CompanySpecial.COMPANY_ID, companyId));
    SpecialDeduction specialDeduction = specialDeductionMapper.selectOne(new QueryWrapper<SpecialDeduction>().like(SpecialDeduction.NAME, "公积金"));
    for (CompanySpecial companySpecial : companySpecialList){
      TaxSpecial taxSpecial = taxSpecialMapper.selectOne(new QueryWrapper<TaxSpecial>().eq(TaxSpecial.TAX_ID, tax.getId()).eq(TaxSpecial.SPECIAL_DEDU_ID, companySpecial.getSpecialId()));
      if (specialDeduction.getId().equals(companySpecial.getSpecialId())){
        if(taxSpecial != null) {
          taxSpecial.setUpdater(SecurityKit.currentId()).setUpdateTime(date).setCompanyMoney(fundBase.multiply(fundPer))
            .setPersonalMoney(fundBase.multiply(fundPer)).setCompanyPercent(fundPer).setPersonalPercent(fundPer);
          taxSpecialMapper.updateById(taxSpecial);
        } else {
          taxSpecial = new TaxSpecial();
          taxSpecial.setUpdater(SecurityKit.currentId()).setUpdateTime(date).setCompanyMoney(fundBase.multiply(fundPer))
            .setPersonalMoney(fundBase.multiply(fundPer)).setCompanyPercent(fundPer).setPersonalPercent(fundPer)
            .setTaxId(tax.getId()).setSpecialDeduId(companySpecial.getSpecialId());
          taxSpecialMapper.insert(taxSpecial);
        }
      } else {
        if (taxSpecial != null) {
          BigDecimal companyPer = companySpecial.getCompanyPer();
          BigDecimal personPer = companySpecial.getPersonPer();
          taxSpecial.setUpdateTime(date).setUpdater(SecurityKit.currentId()).setCompanyMoney(socialBase.multiply(companyPer))
            .setPersonalMoney(socialBase.multiply(personPer)).setCompanyPercent(companyPer).setPersonalPercent(personPer);
          taxSpecialMapper.updateById(taxSpecial);
        } else {
          BigDecimal companyPer = companySpecial.getCompanyPer();
          BigDecimal personPer = companySpecial.getPersonPer();
          taxSpecial = new TaxSpecial();
          taxSpecial.setUpdateTime(date).setUpdater(SecurityKit.currentId()).setCompanyMoney(socialBase.multiply(companyPer))
            .setPersonalMoney(socialBase.multiply(personPer)).setCompanyPercent(companyPer).setPersonalPercent(personPer)
            .setTaxId(tax.getId()).setSpecialDeduId(companySpecial.getSpecialId());
          taxSpecialMapper.insert(taxSpecial);
        }
      }
    }
  }

  public Result findAttendanceList(TaxFindForm taxFindForm) {
    excelService.isHasProperty(taxFindForm.getCompanyId());
    Page<TaxListVo> page = commonService.find(taxFindForm, (p, q) -> taxMapper.selectTaxVoList(p, BeanUtil.beanToMap(taxFindForm)));
    List<TaxListVo> list = page.getRecords();
    for (TaxListVo taxListVo :list){
      Long memberId = taxListVo.getMemberId();
      CompanyUser companyUser = companyUserMapper.selectOne(new QueryWrapper<CompanyUser>().eq(CompanyUser.COMPANY_ID, taxFindForm.getCompanyId()).eq(CompanyUser.MEMBER_ID, memberId));
      CompanyUserInfo companyUserInfo = companyUserInfoMapper.selectById(companyUser.getId());
      NaturalPerson naturalPerson = naturalPersonMapper.selectOne(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.MEMBER_ID, memberId));
      if(companyUserInfo == null || naturalPerson == null){
        return Result.success(null);
      }
      taxListVo.setIdCard(naturalPerson.getCertNum()).setName(naturalPerson.getName()).setCertificate(naturalPerson.getCertificate()).
        setSocialBase(companyUserInfo.getSocialBase()!=null?companyUserInfo.getSocialBase().toString():null).setFundBase(companyUserInfo.getFundBase()!=null?companyUserInfo.getFundBase().toString():null).setFundPer(companyUserInfo.getFundPer()!=null?companyUserInfo.getFundPer().toString():null);
      taxListVo.setAttendanceTaxVo(taxDeclarationService.findTaxInfo(taxListVo.getId()));
    }
    return Result.success(page);
  }


  public Result findAttendanceVoList(TaxFindForm taxFindForm) {
    excelService.isHasProperty(taxFindForm.getCompanyId());
    Page<AttendanceTaxVo> page = commonService.find(taxFindForm, (p, q) -> taxMapper.selectTaxVoList(p, BeanUtil.beanToMap(taxFindForm)));
    List<AttendanceTaxVo> list = page.getRecords();
    for (AttendanceTaxVo attendanceTaxVo :list){
      Long memberId = attendanceTaxVo.getMemberId();
      CompanyUser companyUser = companyUserMapper.selectOne(new QueryWrapper<CompanyUser>().eq(CompanyUser.COMPANY_ID, taxFindForm.getCompanyId()).eq(CompanyUser.MEMBER_ID, memberId));
      CompanyUserInfo companyUserInfo = companyUserInfoMapper.selectById(companyUser.getId());
      NaturalPerson naturalPerson = naturalPersonMapper.selectOne(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.MEMBER_ID, memberId));
      if(companyUserInfo == null || naturalPerson == null){
        return Result.success(null);
      }
      attendanceTaxVo.setIdCard(naturalPerson.getCertNum()).setName(naturalPerson.getName()).setCertificate(naturalPerson.getCertificate()).
        setSocialBase(companyUserInfo.getSocialBase().toString()).setFundBase(companyUserInfo.getFundBase().toString()).setFundPer(companyUserInfo.getFundPer().toString());
      attendanceTaxVo.setDataList(getList(attendanceTaxVo.getId()));
    }
    return Result.success(page);
  }

  public List<AttendanceData> getList(Long taxId) {
    List<AttendanceData> list = new ArrayList<>();
    List<Attendance> attendanceList = attendanceMapper.selectList(new QueryWrapper<Attendance>().eq(Attendance.TAX_ID, taxId));
    List<TaxIncome> taxIncomeList = taxIncomeMapper.selectList(new QueryWrapper<TaxIncome>().eq(TaxIncome.TAX_ID, taxId));
    List<TaxSpecialAdd> taxSpecialAddList = taxSpecialAddMapper.selectList(new QueryWrapper<TaxSpecialAdd>().eq(TaxSpecialAdd.TAX_ID, taxId));
    List<TaxOtherReduce> taxOtherReduceList = taxOtherReduceMapper.selectList(new QueryWrapper<TaxOtherReduce>().eq(TaxOtherReduce.TAX_ID, taxId));
    if (CollectionUtil.isNotEmpty(attendanceList)) {
      for (Attendance attendance : attendanceList) {
        Attribute attribute = attributeMapper.selectOne(new QueryWrapper<Attribute>().eq(Attribute.TABLE_NAME, TableNameEnum.T_ATTENDANCE.getCode())
          .eq(Attribute.ITEM_ID, attendance.getId()));
        if (attribute != null) {
          AttendanceData attendanceData = new AttendanceData();
          attendanceData.setId(attribute.getId()).setValue(attendance.getValue());
          list.add(attendanceData);
        }
      }
    }
    if (CollectionUtil.isNotEmpty(taxIncomeList)) {
      for (TaxIncome taxIncome : taxIncomeList) {
        Attribute attribute = attributeMapper.selectOne(new QueryWrapper<Attribute>().eq(Attribute.TABLE_NAME, TableNameEnum.T_INCOME_TYPE.getCode())
          .eq(Attribute.ITEM_ID, taxIncome.getId()));
        if (attribute != null) {
          AttendanceData attendanceData = new AttendanceData();
          attendanceData.setId(attribute.getId()).setValue(taxIncome.getIncome().toString());
          list.add(attendanceData);
        }
      }
    }
    if (CollectionUtil.isNotEmpty(taxSpecialAddList)) {
      for (TaxSpecialAdd taxSpecialAdd : taxSpecialAddList) {
        Attribute attribute = attributeMapper.selectOne(new QueryWrapper<Attribute>().eq(Attribute.TABLE_NAME, TableNameEnum.T_ADD_SPECIAL.getCode())
          .eq(Attribute.ITEM_ID, taxSpecialAdd.getId()));
        if (attribute != null) {
          AttendanceData attendanceData = new AttendanceData();
          attendanceData.setId(attribute.getId()).setValue(taxSpecialAdd.getTaxMoney().toString());
          list.add(attendanceData);
        }
      }
    }
    if (CollectionUtil.isNotEmpty(taxOtherReduceList)) {
      for (TaxOtherReduce taxOtherReduce : taxOtherReduceList) {
        Attribute attribute = attributeMapper.selectOne(new QueryWrapper<Attribute>().eq(Attribute.TABLE_NAME, TableNameEnum.T_OTHER_REDUCE.getCode())
          .eq(Attribute.ITEM_ID, taxOtherReduce.getId()));
        if (attribute != null) {
          AttendanceData attendanceData = new AttendanceData();
          attendanceData.setId(attribute.getId()).setValue(taxOtherReduce.getTaxMoney().toString());
          list.add(attendanceData);
        }
      }
    }
    return list;
  }
}
