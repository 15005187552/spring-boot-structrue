package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.CompanyUserInfo;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.entity.Tax;
import com.ljwm.gecko.base.mapper.CompanyUserInfoMapper;
import com.ljwm.gecko.base.mapper.NaturalPersonMapper;
import com.ljwm.gecko.base.mapper.TaxMapper;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.dao.AttendanceDao;
import com.ljwm.gecko.client.model.dto.PersonInfoForm;
import com.ljwm.gecko.client.model.dto.SallaryForm;
import com.ljwm.gecko.client.model.vo.NaturalPersonVo;
import com.ljwm.gecko.client.model.vo.SallaryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/4 20:12
 */
@Service
public class PersonInfoService {

  @Autowired
  ApplicationInfo appInfo;
  @Autowired
  NaturalPersonMapper naturalPersonMapper;
  @Autowired
  TaxMapper taxMapper;

  @Autowired
  CompanyUserInfoMapper companyUserInfoMapper;

  @Autowired
  TaxIncomeService taxIncomeService;

  @Autowired
  AttendanceDao attendanceDao;

  @Autowired
  TaxSpecialService taxSpecialService;

  @Autowired
  CalcService calcService;

  @Transactional
  public Result commit(PersonInfoForm personInfoForm){
    NaturalPerson naturalPerson = new NaturalPerson();
    BeanUtil.copyProperties(personInfoForm, naturalPerson);
    Date date = new Date();
    naturalPerson.setUpdateTime(date);
    NaturalPerson naturalPersonFind = naturalPersonMapper.selectById(personInfoForm.getMemberId());
    if (naturalPersonFind != null) {
    } else {
      naturalPerson.setCreatTime(date);
      naturalPersonMapper.insert(naturalPerson);
      File file = new File(appInfo.getFilePath()+ Constant.PERSON + personInfoForm.getMemberId());
      if(!file.exists()){
        file.mkdirs();
      }
    }
    String destDir = appInfo.getFilePath() + Constant.PERSON + naturalPerson.getMemberId() + "/";
    if (StrUtil.isNotBlank(personInfoForm.getDisablityPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getDisablityPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      if(!naturalPerson.getDisablityPath().contains("/")) {
        naturalPerson.setDisablityPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getDisablityPath());
      }
    }
    if (StrUtil.isNotBlank(personInfoForm.getAcademicPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getAcademicPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      if(!naturalPerson.getAcademicPath().contains("/")) {
        naturalPerson.setAcademicPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getAcademicPath());
      }
    }
    if (StrUtil.isNotBlank(personInfoForm.getOldPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getOldPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      if(!naturalPerson.getOldPath().contains("/")) {
        naturalPerson.setOldPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getOldPath());
      }
    }
    if (StrUtil.isNotBlank(personInfoForm.getMatrtyrPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getMatrtyrPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      if(!naturalPerson.getMatrtyrPath().contains("/")) {
        naturalPerson.setMatrtyrPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getMatrtyrPath());
      }
    }
    if (StrUtil.isNotBlank(personInfoForm.getProfessorPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getProfessorPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      if(!naturalPerson.getProfessorPath().contains("/")) {
        naturalPerson.setProfessorPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getProfessorPath());
      }
    }
    naturalPersonMapper.updateById(naturalPerson);
    return Result.success("成功！");
  }

  public Result findByMemberId(Long memberId) {
    NaturalPerson naturalPerson= naturalPersonMapper.selectById(memberId);
    if (naturalPerson != null) {
      NaturalPersonVo naturalPersonVo = new NaturalPersonVo();
      BeanUtil.copyProperties(naturalPerson, naturalPersonVo);
     /* if (StrUtil.isNotBlank(naturalPersonVo.getAcademicPath())) {
        naturalPersonVo.setAcademicPath(appInfo.getWebPath() + naturalPersonVo.getAcademicPath());
      }
      if (StrUtil.isNotBlank(naturalPersonVo.getDisablityPath())) {
        naturalPersonVo.setDisablityPath(appInfo.getWebPath() + naturalPersonVo.getDisablityPath());
      }
      if (StrUtil.isNotBlank(naturalPersonVo.getMatrtyrPath())) {
        naturalPersonVo.setMatrtyrPath(appInfo.getWebPath() + naturalPersonVo.getMatrtyrPath());
      }
      if (StrUtil.isNotBlank(naturalPersonVo.getOldPath())) {
        naturalPersonVo.setOldPath(appInfo.getWebPath() + naturalPersonVo.getOldPath());
      }
      if (StrUtil.isNotBlank(naturalPersonVo.getProfessorPath())) {
        naturalPersonVo.setProfessorPath(appInfo.getWebPath() + naturalPersonVo.getProfessorPath());
      }*/
      return Result.success(naturalPersonVo);
    }
    return Result.success(null);
  }

  public Result findSallary(SallaryForm sallaryForm) {
    Tax tax = taxMapper.selectOne(new QueryWrapper<Tax>().eq(Tax.DECLARE_TIME, sallaryForm.getDeclareTime()));
    Long taxId = tax.getId();
    SallaryVo sallaryVo = new SallaryVo();
    NaturalPerson naturalPerson = naturalPersonMapper.selectOne(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.MEMBER_ID, SecurityKit.currentId()));
    if (naturalPerson != null){
      sallaryVo.setName(naturalPerson.getName());
      if(naturalPerson.getCompanyId() != null){
        List<CompanyUserInfo> list = companyUserInfoMapper.selectCompanyUser(naturalPerson.getCompanyId(), naturalPerson.getMemberId());
        sallaryVo.setJobNum(CollectionUtil.isNotEmpty(list)?list.get(0).getJobNum():null);
      }
    }
    sallaryVo.setBaseSallary(taxIncomeService.getMoney(taxId, "基本工资").toString());
    BigDecimal bonus = new BigDecimal(attendanceDao.selectByAttribute("定期奖金", taxId))
      .add(new BigDecimal(attendanceDao.selectByAttribute("考勤绩效", taxId)))
      .add(new BigDecimal(attendanceDao.selectByAttribute("其他工资收入", taxId)));
    BigDecimal sickDeduc = new BigDecimal(attendanceDao.selectByAttribute("事假", taxId))
      .add(new BigDecimal(attendanceDao.selectByAttribute("病假", taxId)))
      .add(new BigDecimal(attendanceDao.selectByAttribute("迟到早退", taxId)))
      .add(new BigDecimal(attendanceDao.selectByAttribute("其他缺勤", taxId)));
    BigDecimal otherDeduc = new BigDecimal(attendanceDao.selectByAttribute("旷工", taxId))
      .add(new BigDecimal(attendanceDao.selectByAttribute("离岗", taxId)))
      .add(new BigDecimal(attendanceDao.selectByAttribute("罚款", taxId)))
      .add(new BigDecimal(attendanceDao.selectByAttribute("其他处罚", taxId)));
    BigDecimal social = taxSpecialService.getSocialMoney(taxId);
    BigDecimal fund = taxSpecialService.getFundMoney(taxId);
    String yearBonus = attendanceDao.selectByAttribute("全年一次性奖金补贴", taxId);
    String compensation = attendanceDao.selectByAttribute("解除劳动关系一次性经济补偿金", taxId);
    String avaSallery = attendanceDao.selectByAttribute("本地区上年度平均职工工资", taxId);
    BigDecimal income = taxIncomeService.getIncomeMoney(taxId);
    BigDecimal yearDeduc = calYearBonus(new BigDecimal(yearBonus));
    BigDecimal compensationDeduc = BigDecimal.ZERO;
    if(new BigDecimal(compensation).subtract(new BigDecimal(avaSallery).multiply(new BigDecimal("3"))).compareTo(BigDecimal.ZERO)>=0){
      compensationDeduc = calYearBonus(new BigDecimal(compensation).subtract(new BigDecimal(avaSallery).multiply(new BigDecimal("3"))));
    }
    BigDecimal incomeDeduc = calcService.calNew(income.add(bonus).subtract(sickDeduc).subtract(otherDeduc).subtract(social).subtract(fund), new BigDecimal("5000"));
    sallaryVo.setBonus(bonus.toString()).setBaseSallary(sickDeduc.toString()).setOtherDeduc(otherDeduc.toString())
      .setPersonSocial(social.toString()).setPersonFund(fund.toString())
      .setYearBonus(yearBonus)
      .setCompensation(compensation)
      .setPersonTax(incomeDeduc.add(yearDeduc).add(compensationDeduc).toString());
    sallaryVo.setAfterTax(income.add(bonus).subtract(sickDeduc).subtract(otherDeduc).subtract(social).subtract(fund).
      add(new BigDecimal(compensation)).add(new BigDecimal(yearBonus)).subtract(new BigDecimal(sallaryVo.getPersonTax())).toString());
    return Result.success(sallaryVo);
  }

  private BigDecimal calYearBonus(BigDecimal yearBonus) {
    BigDecimal monthBonus = yearBonus.divide(new BigDecimal("12"));
    BigDecimal newPort = new BigDecimal("5000");
    BigDecimal newTax;
    if(monthBonus.compareTo(new BigDecimal("85000"))>=0){
      newTax = (yearBonus.subtract(newPort).multiply(new BigDecimal("0.45")).subtract(new BigDecimal("15160")));
    } else if(monthBonus.compareTo(new BigDecimal("60000"))>=0){
      newTax = (yearBonus.subtract(newPort).multiply(new BigDecimal("0.35")).subtract(new BigDecimal("7160")));
    } else if(monthBonus.compareTo(new BigDecimal("40000"))>=0){
      newTax = (yearBonus.subtract(newPort).multiply(new BigDecimal("0.30")).subtract(new BigDecimal("4410")));
    } else if(monthBonus.compareTo(new BigDecimal("30000"))>=0){
      newTax = (yearBonus.subtract(newPort).multiply(new BigDecimal("0.25")).subtract(new BigDecimal("2660")));
    } else if(monthBonus.compareTo(new BigDecimal("17000"))>=0){
      newTax = (yearBonus.subtract(newPort).multiply(new BigDecimal("0.20")).subtract(new BigDecimal("1410")));
    } else if(monthBonus.compareTo(new BigDecimal("8000"))>=0){
      newTax = (yearBonus.subtract(newPort).multiply(new BigDecimal("0.10")).subtract(new BigDecimal("210")));
    } else if(monthBonus.compareTo(new BigDecimal("5000"))>=0){
      newTax = (yearBonus.subtract(newPort).multiply(new BigDecimal("0.03")));
    } else {
      newTax = BigDecimal.ZERO;
    }
    return newTax;
  }
}
