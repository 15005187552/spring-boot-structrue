package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
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
import com.ljwm.gecko.base.model.dto.EmployeeDto;
import com.ljwm.gecko.base.model.dto.NaturalPersonDto;
import com.ljwm.gecko.base.utils.EnumUtil;
import com.ljwm.gecko.base.utils.excelutil.ExcelLogs;
import com.ljwm.gecko.base.utils.excelutil.ExcelUtil;
import com.ljwm.gecko.client.dao.CompanyUserDao;
import com.ljwm.gecko.client.model.dto.PersonInfoDto;
import com.ljwm.gecko.client.model.vo.PersonExportVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

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
    findMap.put("MEMBER_ID", SecurityKit.currentId());
    findMap.put("COMPANY_ID", companyId);
    findMap.put("DISABLED", DisabledEnum.ENABLED.getCode());
    findMap.put("ACTIVATED", ActivateEnum.ENABLED.getCode());
    List<CompanyUser> companyUserList = companyUserMapper.selectByMap(findMap);
    if(CollectionUtil.isEmpty(companyUserList)){
      throw new LogicException("你没有该操作的权限");
    }
    String roleCode = companyUserList.get(0).getRolesCode();
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

  public String exportPersonInfoExcel(HttpServletResponse response, Long companyId) throws IOException {
    Long memberId = SecurityKit.currentId();
    List<CompanyUser> list = companyUserMapper.selectList(new QueryWrapper<CompanyUser>()
        .eq(CompanyUser.COMPANY_ID, companyId).eq(CompanyUser.MEMBER_ID, memberId)
        .eq(CompanyUser.DISABLED, DisabledEnum.ENABLED.getCode()).eq(CompanyUser.ACTIVATED, DisabledEnum.ENABLED.getCode()));
    if(CollectionUtil.isEmpty(list)){
      throw new LogicException("你没有该操作的权限");
    }
    CompanyUser companyUser = list.get(0);
    String roleCode = companyUser.getRolesCode();
    int c = roleCode.length()- RoleCodeType.ITIN.getDigit();
    Integer code = Integer.valueOf(roleCode.substring(c, c+1));
    if (!code.equals(RoleCodeType.ITIN.getValue())){
      throw new LogicException("你没有该操作的权限");
    }
    List<NaturalPersonDto> naturalPersonDtoList = naturalPersonMapper.selectByCompanyId(companyId);
    if(CollectionUtil.isEmpty(naturalPersonDtoList)){
      throw new LogicException("该公司下没有要导出的员工");
    }
    Map<String, String> map = new LinkedHashMap<>();
    int i = 0;
    String[] str = new String[]{"工号", "*姓名", "*证照类型", "*证照号码", "*国籍(地区)", "性别", "出生年月", "*人员状态",
      "*是否雇员", "*手机号码", "是否残疾", "是否烈属", "是否孤老", "残疾证号", "烈属证号", "任职受雇日期", "离职日期", "电子邮箱", "学历",
      "职业", "开户银行", "银行账号", "是否特定行业", "是否股东、投资者", "个人股本（投资）额", "户籍所在省份", "户籍所在城市", "户籍所在区（县）",
      "户籍所在详细地址","居住省份", "居住城市", "居住所在区（县）", "居住详细地址", "备注", "是否境外人员", "姓名（中文）", "境内有无住所",
      "境外纳税人识别号", "出生地", "首次入境时间", "来华时间", "任职期限", "预计离境时间", "预计离境地点", "境内职务", "境外职务", "支付地",
      "境外支付地（国别/地区）", "*境内任职受雇单位名称", "*境内任职受雇单位扣缴义务人编码", "*境内任职受雇单位地址", "*境内任职受雇单位邮政编码"};
    for (String string: str) {
      map.put(String.valueOf(i), string);
      i++;
    }
    List<Object> objectList = new ArrayList<>();
    for (NaturalPersonDto naturalPersonDto : naturalPersonDtoList) {
      PersonExportVo personExportVo = new PersonExportVo();
      BeanUtil.copyProperties(naturalPersonDto, personExportVo);
      if(StrUtil.isNotBlank(personExportVo.getDisablityNum())){
        personExportVo.setIsDisability("是");
      }
      if(StrUtil.isNotBlank(personExportVo.getMatrtyrNum())){
        personExportVo.setIsMatrtyr("是");
      }
      if(StrUtil.isNotBlank(personExportVo.getProvince())){
        personExportVo.setProvince(locationDao.getNameByCode(personExportVo.getProvince()));
      }
      if(StrUtil.isNotBlank(personExportVo.getCity())){
        personExportVo.setCity(locationDao.getNameByCode(personExportVo.getCity()));
      }
      if(StrUtil.isNotBlank(personExportVo.getArea())){
        personExportVo.setArea(locationDao.getNameByCode(personExportVo.getArea()));
      }
      if(StrUtil.isNotBlank(personExportVo.getFamilyProvince())){
        personExportVo.setFamilyProvince(locationDao.getNameByCode(personExportVo.getFamilyProvince()));
      }
      if(StrUtil.isNotBlank(personExportVo.getFamilyCity())){
        personExportVo.setFamilyCity(locationDao.getNameByCode(personExportVo.getFamilyCity()));
      }
      if(StrUtil.isNotBlank(personExportVo.getFamilyArea())){
        personExportVo.setFamilyArea(locationDao.getNameByCode(personExportVo.getFamilyArea()));
      }
      List<EmployeeDto> employeeDtoList = companyUserMapper.selectEmployeeList(companyId, memberId);
      BeanUtil.copyProperties(employeeDtoList.get(0).getCompanyUserInfo(), personExportVo);
      if(StrUtil.isNotBlank(personExportVo.getEducation())){
        personExportVo.setEducation(EnumUtil.getNameBycode(EducationEnum.class, Integer.valueOf(personExportVo.getEducation())));
      }
      if(StrUtil.isNotBlank(personExportVo.getPersonState())){
        personExportVo.setPersonState(EnumUtil.getNameBycode(PersonStateEnum.class, Integer.valueOf(personExportVo.getPersonState())));
      }
      if(StrUtil.isNotBlank(personExportVo.getEmployee())){
        personExportVo.setPersonState(EnumUtil.getNameBycode(YesOrNoEnum.class, Integer.valueOf(personExportVo.getEmployee())));
      }

    }
    response.reset();
    response.setContentType("multipart/form-data");
    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("人员信息.xls","UTF-8"));
    OutputStream output = response.getOutputStream();
    ExcelUtil.exportExcel(map, objectList, output);
    output.close();
    return "导出成功！";
  }
}
