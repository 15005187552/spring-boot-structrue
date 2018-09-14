package com.ljwm.gecko.client.service;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.base.dao.LocationDao;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.entity.CompanyUserInfo;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.enums.*;
import com.ljwm.gecko.base.mapper.CompanyUserInfoMapper;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.mapper.NaturalPersonMapper;
import com.ljwm.gecko.base.utils.EnumUtil;
import com.ljwm.gecko.base.utils.excelutil.ExcelLogs;
import com.ljwm.gecko.base.utils.excelutil.ExcelUtil;
import com.ljwm.gecko.client.dao.CompanyUserDao;
import com.ljwm.gecko.client.model.dto.AttendanceDto;
import com.ljwm.gecko.client.model.dto.PersonInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/11 13:10
 */
@Slf4j
@Service
public class ExcelService {

  @Autowired
  MemberMapper memberMapper;

  @Autowired
  CompanyUserMapper companyUserMapper;

  @Autowired
  NaturalPersonMapper naturalPersonMapper;

  @Autowired
  CompanyUserInfoMapper companyUserInfoMapper;

  @Autowired
  CompanyUserDao companyUserDao;

  @Autowired
  LocationDao locationDao;

  @Transactional
  public void improtPersonInfo(MultipartFile file, Long companyId) throws Exception {
    Map<String, Object> findMap = new HashedMap();
    findMap.put("MEMBER_ID", 3);
    findMap.put("COMPANY_ID", companyId);
    findMap.put("DISABLED", DisabledEnum.ENABLED.getCode());
    findMap.put("ACTIVATED", ActivateEnum.ENABLED.getCode());
    List<CompanyUser> companyUserList = companyUserMapper.selectByMap(findMap);
    if(CollectionUtil.isEmpty(companyUserList)){
      throw new LogicException("你没有该操作的权限");
    }
    String roleCode = companyUserList.get(0).getRolesCode().toString();
    int c = roleCode.length()- RoleCodeType.ITIN.getDigit();
    Integer code = Integer.valueOf(roleCode.substring(c, c+1));
    if (!code.equals(RoleCodeType.ITIN.getValue())){
      throw new LogicException("你没有该操作的权限");
    }
    InputStream inputStream = file.getInputStream();
    ExcelLogs logs =new ExcelLogs();
    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);
    Field[] fields = PersonInfoDto.class.getDeclaredFields();
    String[] fieldNames = new String[fields.length];
    int i = 0;
    for (Field f : fields) {
      fieldNames[i] = f.getName();
      i++;
    }
    for(Map m:importExcel){
      PersonInfoDto personInfoDto = new PersonInfoDto();
      int index = 0;
      for (Object key:m.keySet()){
        Field temFiels = PersonInfoDto.class.getDeclaredField(fieldNames[index]);
        temFiels.setAccessible(true);
        temFiels.set(personInfoDto, m.get(key));
        index++;
      }
      Map<String, Object> map = new HashedMap();
      map.put("REG_MOBILE", personInfoDto.getRegMobile());
      List<Member> list = memberMapper.selectByMap(map);
      Long memberId;
      if(CollectionUtil.isNotEmpty(list)){
        memberId = list.get(0).getId();
      } else {
        //发送短信邀请码  并且插入数据库
        Member member = new Member();
        member.setRegMobile(personInfoDto.getRegMobile())
              .setCreateTime(new Date())
              .setDisabled(DisabledEnum.DISABLED.getInfo());
        memberMapper.insert(member);
        memberId = member.getId();
      }
     /* Integer provinceCode = locationDao.getProvinceCode(personInfoDto.getProvince());
      Integer cityCode = locationDao.getCityCode(personInfoDto.getCity(), provinceCode);
      Integer areaCode = locationDao.getAreaCode(personInfoDto.getArea(), cityCode);*/
      NaturalPerson naturalPerson = new NaturalPerson();
      naturalPerson.setMemberId(memberId)
        .setCountry(EnumUtil.getEnumByName(CountryType.class, personInfoDto.getCountry()).getCode())
        .setName(personInfoDto.getName())
        /*.setGender(EnumUtil.getEnumByName(GenderEnum.class, personInfoDto.getGender()).getCode())
        .setBirthday(TimeUtil.parseString(personInfoDto.getBirthday()))*/
        .setCertificate(EnumUtil.getEnumByName(CertificateType.class, personInfoDto.getCertificate()).getCode())
        /*.setProvince(provinceCode)
        .setCity(cityCode)
        .setArea(areaCode)
        .setAddress(personInfoDto.getAddress())*/
        .setCertNum(personInfoDto.getCertNum());
       /* .setDisablityNum(personInfoDto.getDisablityNum())
        .setMatrtyrNum(personInfoDto.getMatrtyrNum());*/
      NaturalPerson naturalPerson1 = naturalPersonMapper.selectById(memberId);
      if(naturalPerson1 != null){
        naturalPersonMapper.updateById(naturalPerson);
      } else {
        naturalPersonMapper.insert(naturalPerson);
      }
      String[] str = new String[RoleCodeType.values().length];
      StringBuffer stringBuffer = new StringBuffer();
      for (int j = 0; j < str.length; j++) {
        str[j] = "0";
        stringBuffer.append(str[j]);
      }
      String role = stringBuffer.toString();
      CompanyUser companyUser = companyUserDao.insertOrUpdate(memberId, companyId, role);
      Long companyUserId = companyUser.getId();
      CompanyUserInfo companyUserInfo = companyUserInfoMapper.selectById(companyUserId);
      CompanyUserInfo companyUserInfo1 = new CompanyUserInfo();
      //Integer workCity = locationDao.getCityCode(personInfoDto.getWorkCity(), null);
      companyUserInfo1.setCompanyUserId(companyUserId)
        .setJobNum(personInfoDto.getJobNum())
        //.setEducation(EnumUtil.getEnumByName(EducationEnum.class, personInfoDto.getEducation()).getCode())
        .setPersonState(EnumUtil.getEnumByName(PersonStateEnum.class, personInfoDto.getPersonState()).getCode())
        .setEmployee(EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getEmployee()).getCode())
      /*  .setHireDate(TimeUtil.parseString(personInfoDto.getHireDate()))
        .setEmployeeType(personInfoDto.getEmployeeType())
        .setDepartment(personInfoDto.getDepartment())
        .setStation(personInfoDto.getStation())
        .setTermDate(TimeUtil.parseString(personInfoDto.getTermDate()))*/
        .setSocialBase(new BigDecimal(personInfoDto.getSocialBase()))
       /* .setSocialComPer(new BigDecimal(personInfoDto.getSocialComPer()))
        .setComPension(new BigDecimal(personInfoDto.getComPension()))
        .setComMedical(new BigDecimal(personInfoDto.getComMedical()))
        .setComUnemploy(new BigDecimal(personInfoDto.getComUnemploy()))
        .setComInjury(new BigDecimal(personInfoDto.getComInjury()))
        .setComBirth(new BigDecimal(personInfoDto.getComBirth()))
        .setSocialPersonPer(new BigDecimal(personInfoDto.getSocialPersonPer()))
        .setPersonPension(new BigDecimal(personInfoDto.getPersonPension()))
        .setPersonMedical(new BigDecimal(personInfoDto.getPersonMedical()))
        .setPersonUnemploy(new BigDecimal(personInfoDto.getPersonUnemploy()))*/
        .setFundBase(new BigDecimal(personInfoDto.getFundBase()));
       /* .setFundCom(new BigDecimal(personInfoDto.getFundCom()))
        .setFundPerson(new BigDecimal(personInfoDto.getFundPerson()))
        .setWorkCity(workCity);
        .setMaritalStatus(EnumUtil.getEnumByName(MaritalStatusEnum.class, personInfoDto.getMaritalStatus()).getCode())
        .setNtroduceTalents(EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getNtroduceTalents()).getCode())
        .setBank(personInfoDto.getBank())
        .setBankNum(personInfoDto.getBankNum())
        .setSocialNum(personInfoDto.getSocialNum())
        .setFundNum(personInfoDto.getFundNum())
        .setSpecialIndustry(EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getSpecialIndustry()).getCode())
        .setIsInvestor(EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getIsInvestor()).getCode())
        .setEmail(personInfoDto.getEmail())
        .setRemark(personInfoDto.getRemark());*/
      if(companyUserInfo != null) {
        companyUserInfoMapper.updateById(companyUserInfo1);
      } else {
        companyUserInfoMapper.insert(companyUserInfo1);
      }
      log.debug("{}", personInfoDto);
    }
  }


  @Transactional
  public void improtAttendance(MultipartFile file, Long companyId, String date) throws Exception {
    Map<String, Object> findMap = new HashedMap();
    findMap.put("MEMBER_ID", 3);
    findMap.put("COMPANY_ID", companyId);
    findMap.put("DISABLED", DisabledEnum.ENABLED.getCode());
    findMap.put("ACTIVATED", ActivateEnum.ENABLED.getCode());
    List<CompanyUser> companyUserList = companyUserMapper.selectByMap(findMap);
    if(CollectionUtil.isEmpty(companyUserList)){
      throw new LogicException("你没有该操作的权限");
    }
    String roleCode = companyUserList.get(0).getRolesCode().toString();
    int c = roleCode.length()- RoleCodeType.ITIN.getDigit();
    Integer code = Integer.valueOf(roleCode.substring(c, c+1));
    if (!code.equals(RoleCodeType.ITIN.getValue())){
      throw new LogicException("你没有该操作的权限");
    }
    InputStream inputStream = file.getInputStream();
    ExcelLogs logs =new ExcelLogs();
    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);
    Field[] fields = AttendanceDto.class.getDeclaredFields();
    String[] fieldNames = new String[fields.length];
    int i = 0;
    for (Field f : fields) {
      fieldNames[i] = f.getName();
      i++;
    }
    for(Map m:importExcel){
      log.debug("{}", m);
    }
  }
}
