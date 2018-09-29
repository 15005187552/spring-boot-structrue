package com.ljwm.gecko.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.enums.TableNameEnum;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.client.model.dto.AttendanceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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


  @Transactional
  public Result commit(AttendanceForm attendanceForm) {
    Long companyId = attendanceForm.getCompanyId();
    String declareTime = attendanceForm.getDeclareTime();
    List<AttendanceForm.AttendanceDto> list = attendanceForm.getList();
    for (AttendanceForm.AttendanceDto attendanceDto : list){
      BigDecimal socialBase = new BigDecimal(attendanceDto.getSocialBase());
      BigDecimal fundBase = new BigDecimal(attendanceDto.getFundBase());
      BigDecimal fundPer = new BigDecimal(attendanceDto.getFundPer());
      String certificate = attendanceDto.getCertificate();
      String idCard = attendanceDto.getIdCard();
      NaturalPerson naturalPerson = naturalPersonMapper.selectOne(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.CERT_NUM, idCard).eq(NaturalPerson.CERTIFICATE, certificate));
      if (naturalPerson != null) {
        Long memberId = naturalPerson.getMemberId();
        Tax tax = taxMapper.selectOne(new QueryWrapper<Tax>().eq(Tax.DECLARE_TIME, declareTime).eq(Tax.MEMBER_ID, memberId).eq(Tax.DECLARE_TYPE, attendanceForm.getDeclareType()));
        Date date = new Date();
        tax.setUpdateTime(date);
        if (tax != null){
          taxMapper.updateById(tax);
        } else {
          tax.setCreateTime(date);
          taxMapper.insert(tax);
        }
        List<AttendanceForm.AttendanceDto.AttendanceData> dataList = attendanceDto.getDataList();
        for (AttendanceForm.AttendanceDto.AttendanceData attendanceData : dataList){
          Attribute attribute = attributeMapper.selectById(attendanceData.getId());
          Integer tableName = attribute.getTableName();
          Long itemId = attribute.getItemId();
          String value = attendanceData.getValue();
          if(tableName == TableNameEnum.T_INCOME_TYPE.getCode()){
            TaxIncome taxIncome = taxIncomeMapper.selectOne(new QueryWrapper<TaxIncome>().eq(TaxIncome.TAX_ID, tax.getId())
              .eq(TaxIncome.INCOME_TYPE_ID, itemId));
            if (taxIncome != null){
              taxIncome.setUpdateTime(date).setIncome(value).setUpdater(SecurityKit.currentId());
              taxIncomeMapper.updateById(taxIncome);
            } else {
              taxIncome.setUpdateTime(date).setIncome(value).setUpdater(SecurityKit.currentId()).setCreateTime(date);
              taxIncomeMapper.insert(taxIncome);
            }
          }
          if(tableName == TableNameEnum.T_OTHER_REDUCE.getCode()){
            TaxOtherReduce taxOtherReduce = taxOtherReduceMapper.selectOne(new QueryWrapper<TaxOtherReduce>()
              .eq(TaxOtherReduce.TAX_ID, tax.getId()).eq(TaxOtherReduce.OTHER_REDUCE_ID, itemId));
            if (taxOtherReduce != null){
              taxOtherReduce.setUpdateTime(date).setTaxMoney(value).setUpdater(SecurityKit.currentId());
              taxOtherReduceMapper.updateById(taxOtherReduce);
            } else {
              taxOtherReduce.setUpdateTime(date).setTaxMoney(value).setUpdater(SecurityKit.currentId()).setCreateTime(date);
              taxOtherReduceMapper.insert(taxOtherReduce);
            }
          }
         /* if(tableName == TableNameEnum.T_SPECIAL_DEDUCTION.getCode()){
            TaxSpecial taxSpecial = taxSpecialMapper.selectOne(new QueryWrapper<TaxSpecial>()
              .eq(TaxSpecial.TAX_ID, tax.getId()).eq(TaxSpecial.SPECIAL_DEDU_ID, itemId));
            if (taxSpecial != null){
              taxSpecial.setUpdateTime(date).setUpdater(SecurityKit.currentId());
              taxSpecialMapper.updateById(taxSpecial);
            } else {
              taxSpecial.setUpdateTime(date).setUpdater(SecurityKit.currentId()).setCreateTime(date);
              taxSpecialMapper.insert(taxSpecial);
            }
          }*/
          List<CompanySpecial> companySpecialList = companySpecialMapper.selectList(new QueryWrapper<CompanySpecial>().eq(CompanySpecial.COMPANY_ID, companyId));
          for (CompanySpecial companySpecial : companySpecialList){
            SpecialDeduction specialDeduction = specialDeductionMapper.selectOne(new QueryWrapper<SpecialDeduction>().like(SpecialDeduction.NAME, "公积金"));
            TaxSpecial taxSpecial = taxSpecialMapper.selectOne(new QueryWrapper<TaxSpecial>().eq(TaxSpecial.TAX_ID, tax.getId()).eq(TaxSpecial.SPECIAL_DEDU_ID, companySpecial.getSpecialId()));
            if (specialDeduction.getId().equals(companySpecial.getSpecialId())){
              if(taxSpecial != null) {
                taxSpecial.setUpdater(SecurityKit.currentId()).setUpdateTime(date).setCompanyMoney(fundBase.multiply(fundPer).toString())
                  .setPersonalMoney(fundBase.multiply(fundPer).toString()).setCompanyPercent(fundPer.toString()).setPersonalPercent(fundPer.toString());
                taxSpecialMapper.updateById(taxSpecial);
              } else {
                taxSpecial.setUpdater(SecurityKit.currentId()).setUpdateTime(date).setCompanyMoney(fundBase.multiply(fundPer).toString())
                  .setPersonalMoney(fundBase.multiply(fundPer).toString()).setCompanyPercent(fundPer.toString()).setPersonalPercent(fundPer.toString())
                  .setTaxId(tax.getId()).setSpecialDeduId(companySpecial.getSpecialId());
                taxSpecialMapper.updateById(taxSpecial);
              }
            } else {
              if (taxSpecial != null) {
                BigDecimal companyPer = companySpecial.getCompanyPer();
                BigDecimal personPer = companySpecial.getPersonPer();
                taxSpecial.setUpdateTime(date).setUpdater(SecurityKit.currentId()).setCompanyMoney(socialBase.multiply(companyPer).toString())
                  .setPersonalMoney(socialBase.multiply(personPer).toString()).setCompanyPercent(companyPer.toString()).setPersonalPercent(personPer.toString());
                taxSpecialMapper.updateById(taxSpecial);
              } else {
                BigDecimal companyPer = companySpecial.getCompanyPer();
                BigDecimal personPer = companySpecial.getPersonPer();
                taxSpecial.setUpdateTime(date).setUpdater(SecurityKit.currentId()).setCompanyMoney(socialBase.multiply(companyPer).toString())
                  .setPersonalMoney(socialBase.multiply(personPer).toString()).setCompanyPercent(companyPer.toString()).setPersonalPercent(personPer.toString())
                  .setTaxId(tax.getId()).setSpecialDeduId(companySpecial.getSpecialId());
                taxSpecialMapper.insert(taxSpecial);
              }
            }
          }
          if(tableName == TableNameEnum.T_ADD_SPECIAL.getCode()){
            TaxSpecialAdd taxSpecialAdd = taxSpecialAddMapper.selectOne(new QueryWrapper<TaxSpecialAdd>()
              .eq(TaxSpecialAdd.TAX_ID, tax.getId()).eq(TaxSpecialAdd.SPECIAL_ADD_ID, itemId));
            if (taxSpecialAdd != null){
              taxSpecialAdd.setUpdateTime(date).setTaxMoney(value).setUpdater(SecurityKit.currentId());
              taxSpecialAddMapper.updateById(taxSpecialAdd);
            } else {
              taxSpecialAdd.setUpdateTime(date).setTaxMoney(value).setUpdater(SecurityKit.currentId()).setCreateTime(date);
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
              attendance.setValue(value);
              attendanceMapper.insert(attendance);
            }
          }
        }
      }else {
        return Result.fail("证件号码或者证照类型有误！");
      }
    }
    return Result.success("成功！");
  }

}
