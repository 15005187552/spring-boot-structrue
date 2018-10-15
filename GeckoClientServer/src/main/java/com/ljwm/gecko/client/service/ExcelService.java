package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.dao.LocationDao;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.enums.*;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.dto.EmployeeDto;
import com.ljwm.gecko.base.model.dto.NaturalPersonDto;
import com.ljwm.gecko.base.utils.EnumUtil;
import com.ljwm.gecko.base.utils.TimeUtil;
import com.ljwm.gecko.base.utils.excelutil.ExcelLogs;
import com.ljwm.gecko.base.utils.excelutil.ExcelUtil;
import com.ljwm.gecko.client.dao.CompanyUserDao;
import com.ljwm.gecko.client.model.dto.AttendanceModel;
import com.ljwm.gecko.client.model.dto.EmployeeInfoForm;
import com.ljwm.gecko.client.model.dto.NormalSalaryForm;
import com.ljwm.gecko.client.model.dto.PersonInfoDto;
import com.ljwm.gecko.client.model.vo.NormalSalaryVo;
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
import java.text.ParseException;
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

  @Autowired
  CompanySpecialMapper companySpecialMapper;

  @Autowired
  TaxOtherReduceMapper taxOtherReduceMapper;

  @Autowired
  AttributeMapper attributeMapper;

  @Autowired
  AttendanceService attendanceService;

  @Autowired
  TaxMapper taxMapper;

  @Autowired
  NaturalPersonBackupMapper naturalPersonBackupMapper;

  @Autowired
  ApplicationInfo appInfo;

  @Transactional
  public void importPersonInfo(MultipartFile file, Long companyId) throws Exception {
    isHasProperty(companyId);
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
      importEmployeeInfo(personInfoDto, companyId);
      log.debug("{}", personInfoDto);
    }
  }


  @Transactional
  public Result importAttendance(MultipartFile file, Long companyId, String declareTime, Integer declareType) throws IOException {
    isHasProperty(companyId);
    InputStream inputStream = file.getInputStream();
    ExcelLogs logs =new ExcelLogs();
    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);
    Object name=null, certificate = null, certNum = null, socialBase = null, fundBase = null, fundPer = null;
    NaturalPerson naturalPerson = null;
    for(Map m:importExcel){
      for (Object key:m.keySet()) {
        if (key.equals("*姓名")) {
          name = m.get(key);
        }
        if (key.equals("*证照类型")) {
          certificate = m.get(key);
        }
        if (key.equals("*证照号码")) {
          certNum = m.get(key);
        }
        if (key.equals("*社保基数")) {
          socialBase = m.get(key);
        }
        if (key.equals("*公积金基数")) {
          fundBase = m.get(key);
        }
        if (key.equals("*公积金比例")) {
          fundPer = m.get(key);
        }
        if (certificate != null && certNum != null) {
          if(naturalPerson == null){
            Integer certificateType = EnumUtil.getEnumByName(CertificateType.class, certificate.toString()).getCode();
            naturalPerson = naturalPersonMapper.selectOne(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.CERT_NUM, certNum.toString()).eq(NaturalPerson.CERTIFICATE, certificateType));
            if(naturalPerson ==null){
                return Result.fail("员工"+name.toString()+"证件号码或者证照类型有误！");
            }
          }else {
            Long memberId = naturalPerson.getMemberId();
            CompanyUser companyUser = companyUserMapper.selectOne(new QueryWrapper<CompanyUser>().eq(CompanyUser.COMPANY_ID, companyId).eq(CompanyUser.MEMBER_ID, memberId));
            CompanyUserInfo companyUserInfo = companyUserInfoMapper.selectById(companyUser.getId());
            if (companyUserInfo != null) {
              if (fundBase != null) {
                companyUserInfo.setFundBase(new BigDecimal(fundBase.toString()));
              }
              if (fundPer != null) {
                companyUserInfo.setFundPer(new BigDecimal(fundPer.toString()));
              }
              if (socialBase != null) {
                companyUserInfo.setSocialBase(new BigDecimal(socialBase.toString()));
              }
              companyUserInfoMapper.updateById(companyUserInfo);
            }
            Tax tax = taxMapper.selectOne(new QueryWrapper<Tax>().eq(Tax.DECLARE_TIME, declareTime).eq(Tax.MEMBER_ID, memberId));
            Date date = new Date();
            if (tax != null) {
              tax.setUpdateTime(date).setStatus(TaxStatus.NEED_CONFIRM.getCode()).setDeclareType(declareType);
              taxMapper.updateById(tax);
            } else {
              tax = new Tax();
              tax.setCreateTime(date).setUpdateTime(date).setDeclareTime(declareTime).setDeclareType(declareType).setMemberId(memberId)
                .setStatus(TaxStatus.NEED_CONFIRM.getCode());
              taxMapper.insert(tax);
            }
            if (!key.toString().contains("*")) {
              Attribute attribute = attributeMapper.selectOne(new QueryWrapper<Attribute>().eq(Attribute.NAME, key.toString()));
              Long itemId = attribute.getItemId();
              Integer tableName = attribute.getTableName();
              String value = m.get(key).toString();
              attendanceService.insertOrUpdate(tableName, itemId, date, value, tax);
            }
            if (socialBase != null && fundBase != null && fundPer != null) {
              attendanceService.insertOrUpdate(new BigDecimal(socialBase.toString()), new BigDecimal(fundBase.toString()), new BigDecimal(fundPer.toString()), companyId, date, tax);
            }
          }
        }
      }
    }
    return Result.success("导入成功！");
  }


  @Transactional
  public File exportPersonInfoExcel(HttpServletResponse response, Long companyId, String uuid) throws IOException, ParseException {
    Long memberId = isHasProperty(companyId);
    List<NaturalPersonDto> naturalPersonDtoList = naturalPersonMapper.selectByCompanyId(companyId);
    if(CollectionUtil.isEmpty(naturalPersonDtoList)){
      throw new LogicException("该公司下没有要导出的员工");
    }
    Map<String, String> map = new LinkedHashMap<>();
    int i = 0;
    String[] str = {"工号", "*姓名", "*证照类型", "*证照号码", "*国籍(地区)", "性别", "出生年月", "*人员状态",
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
      if (StrUtil.isNotBlank(personExportVo.getBirthday())){
        personExportVo.setBirthday(TimeUtil.parseDate(personExportVo.getBirthday()));
      }
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
      if (CollectionUtil.isEmpty(employeeDtoList)){
        throw new LogicException("该公司下没有对应员工");
      }
      BeanUtil.copyProperties(employeeDtoList.get(0).getCompanyUserInfo(), personExportVo);
      if(StrUtil.isNotBlank(personExportVo.getEducation())){
        personExportVo.setEducation(EnumUtil.getNameBycode(EducationEnum.class, Integer.valueOf(personExportVo.getEducation())));
      }
      if(StrUtil.isNotBlank(personExportVo.getPersonState())){
        personExportVo.setPersonState(EnumUtil.getNameBycode(PersonStateEnum.class, Integer.valueOf(personExportVo.getPersonState())));
      }
      if(StrUtil.isNotBlank(personExportVo.getEmployee())){
        personExportVo.setEmployee(EnumUtil.getNameBycode(YesOrNoEnum.class, Integer.valueOf(personExportVo.getEmployee())));
      }
      objectList.add(personExportVo);
    }
   /* response.reset();
    response.setContentType("multipart/form-data");
    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("人员信息.xlsx","UTF-8"));
    OutputStream output = response.getOutputStream();*/
    File filePath = new File(appInfo.getFilePath()+ Constant.ZIP + uuid);
    if(!filePath.exists()){
      filePath.mkdirs();
    }
    File file = new File(appInfo.getFilePath()+ Constant.ZIP + uuid + "/人员信息.xlsx");
    OutputStream output = new FileOutputStream(file);
    ExcelUtil.exportExcel(map, objectList, output);
    output.close();
    return file;
  }

  @Transactional
  public File exportNormalSalary(HttpServletResponse response, NormalSalaryForm normalSalaryForm, String uuid) throws IOException {
    Long companyId = normalSalaryForm.getCompanyId();
    String declareTime = normalSalaryForm.getDeclareTime();
    isHasProperty(companyId);
    List<NaturalPerson> list = naturalPersonMapper.selectList(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.COMPANY_ID, companyId));
    if (CollectionUtil.isEmpty(list)){
      throw new LogicException("该公司下没有要导出的员工");
    }
    Map<String, String> map = new LinkedHashMap<>();
    int i = 0;
    String[] str = new String[]{"工号", "*姓名", "*证照类型", "*证照号码", "税款负担方式", "*收入额", "免税所得", "基本养老保险费",
      "基本医疗保险费", "失业保险费", "住房公积金", "允许扣除的税费", "年金", "商业健康保险费", "税延养老保险费", "其他扣除", "减除费用",
      "实际捐赠额", "允许列支的捐赠比例","准予扣除的捐赠额", "减免税额", "已扣缴税额", "备注"};
    for (String string: str) {
      map.put(String.valueOf(i), string);
      i++;
    }
    List<Object> dataList = new ArrayList<>();
    for (NaturalPerson naturalPerson : list) {
      Tax tax = taxMapper.selectOne(new QueryWrapper<Tax>().eq(Tax.DECLARE_TIME, normalSalaryForm.getDeclareTime()).eq(Tax.MEMBER_ID, naturalPerson.getMemberId()));
      if (tax != null){
        //备份员工信息
        NaturalPersonBackup naturalPersonBackup = new NaturalPersonBackup();
        BeanUtil.copyProperties(naturalPerson, naturalPersonBackup);
        naturalPersonBackup.setTaxId(tax.getId());
        NaturalPersonBackup naturalPersonBackupFind = naturalPersonBackupMapper.selectOne(new QueryWrapper<NaturalPersonBackup>().eq(NaturalPersonBackup.TAX_ID, tax.getId()));
        if (naturalPersonBackupFind != null){
          naturalPersonBackupMapper.updateById(naturalPersonBackup);
        }else {
          naturalPersonBackupMapper.insert(naturalPersonBackup);
        }
      }
      Long memberId = naturalPerson.getMemberId();
      List<EmployeeDto> employeeDtoList = companyUserMapper.selectEmployeeList(companyId, memberId);
      if (CollectionUtil.isEmpty(employeeDtoList)){
        throw new LogicException("该公司下没有对应员工");
      }
      NormalSalaryVo normalSalaryVo = new NormalSalaryVo();
      BeanUtil.copyProperties(naturalPerson, normalSalaryVo);
      /*BigDecimal socialBase = employeeDtoList.get(0).getCompanyUserInfo().getSocialBase();
      BigDecimal fundBase = employeeDtoList.get(0).getCompanyUserInfo().getFundBase();
      BigDecimal entire = companySpecialMapper.selectByName("养老保险");
      BigDecimal medical = companySpecialMapper.selectByName("医疗保险");
      BigDecimal unemployee = companySpecialMapper.selectByName("失业保险");
      BigDecimal fund = companySpecialMapper.selectByName("公积金");
      normalSalaryVo.setEntireInsurance(socialBase.multiply(entire).toString());
      normalSalaryVo.setMedicalInsurance(socialBase.multiply(medical).toString());
      normalSalaryVo.setUnemployeeInsurance(socialBase.multiply(unemployee).toString());
      normalSalaryVo.setFund(fundBase.multiply(fund).toString());*/
      BigDecimal annuity = taxOtherReduceMapper.selectTaxMoney(memberId, "年金", declareTime);
      BigDecimal commercialInsurance = taxOtherReduceMapper.selectTaxMoney(memberId, "商业健康保险费", declareTime);
      BigDecimal otherEntireInsurance = taxOtherReduceMapper.selectTaxMoney(memberId, "养老保险", declareTime);
      normalSalaryVo.setAnnuity(annuity!=null?annuity.toString():null).setCommercialInsurance(commercialInsurance!=null?commercialInsurance.toString():null).setOtherEntireInsurance(otherEntireInsurance!=null?otherEntireInsurance.toString():null);
      dataList.add(normalSalaryVo);
    }
    /*response.reset();
    response.setContentType("multipart/form-data");
    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("人员信息.xlsx","UTF-8"));
    OutputStream output = response.getOutputStream();*/
    File file = new File(appInfo.getFilePath()+ Constant.ZIP + uuid+"/正常工资薪金.xlsx");
    OutputStream output = new FileOutputStream(file);
    ExcelUtil.exportExcel(map, dataList, output);
    output.close();
    return file;
  }

  /**
   * 判断当前角色是否是报税员
   * @param companyId
   * @return
   */
  public Long isHasProperty(Long companyId){
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
    return memberId;
  }

  @Transactional
  public Result commitEmployeeInfo(EmployeeInfoForm employeeInfoForm) throws ParseException {
    Long companyId = employeeInfoForm.getCompanyId();
    isHasProperty(companyId);
    List<PersonInfoDto> list = employeeInfoForm.getList();
    for (PersonInfoDto personInfoDto : list){
      importEmployeeInfo(personInfoDto, companyId);
    }
    return Result.success("成功");
  }

  public void importEmployeeInfo(PersonInfoDto personInfoDto, Long companyId) throws ParseException {
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
    String provinceCode = locationDao.getProvinceCode(personInfoDto.getProvince());
    String cityCode = locationDao.getCityCode(personInfoDto.getCity(), provinceCode);
    String areaCode = locationDao.getAreaCode(personInfoDto.getArea(), cityCode);
    NaturalPerson naturalPerson = new NaturalPerson();
    naturalPerson.setMemberId(memberId).setName(personInfoDto.getName()).setProvince(provinceCode)
      .setCity(cityCode)
      .setArea(areaCode)
      .setAddress(personInfoDto.getAddress())
      .setCertNum(personInfoDto.getCertNum())
      .setDisablityNum(personInfoDto.getDisablityNum())
      .setMatrtyrNum(personInfoDto.getMatrtyrNum())
      .setUpdateTime(new Date()).setCompanyId(companyId);
    if (StrUtil.isNotEmpty(personInfoDto.getCountry())) {
      if(EnumUtil.getEnumByName(CountryType.class, personInfoDto.getCountry()) != null){
        naturalPerson.setCountry(EnumUtil.getEnumByName(CountryType.class, personInfoDto.getCountry()).getCode());
      } else {
        throw new LogicException("国籍填写有误！");
      }
    }
    if (StrUtil.isNotEmpty(personInfoDto.getGender())){
      if (EnumUtil.getEnumByName(GenderEnum.class, personInfoDto.getGender()) != null){
        naturalPerson.setGender(EnumUtil.getEnumByName(GenderEnum.class, personInfoDto.getGender()).getCode());
      } else {
        throw new LogicException("性别填写有误！");
      }
    }
    if (StrUtil.isNotEmpty(personInfoDto.getCertificate())){
      if (EnumUtil.getEnumByName(CertificateType.class, personInfoDto.getCertificate()) != null){
        naturalPerson.setCertificate(EnumUtil.getEnumByName(CertificateType.class, personInfoDto.getCertificate()).getCode());
      } else {
        throw new LogicException("证照类型填写有误！");
      }
    }
    if (StrUtil.isNotEmpty(personInfoDto.getBirthday())) {
      naturalPerson.setBirthday(TimeUtil.parseString(personInfoDto.getBirthday()));
    }
    NaturalPerson naturalPerson1 = naturalPersonMapper.selectOne(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.CERTIFICATE, EnumUtil.getEnumByName(CertificateType.class, personInfoDto.getCertificate()).getCode())
      .eq(NaturalPerson.CERT_NUM, personInfoDto.getCertNum()));
    if(naturalPerson1 != null){
      BeanUtil.copyProperties(naturalPerson, naturalPerson1);
      naturalPersonMapper.updateById(naturalPerson1);
    } else {
      naturalPerson.setCreatTime(new Date());
      if(naturalPerson.getMemberId()!=null){
        throw new LogicException(personInfoDto.getRegMobile()+"该手机号码已注册");
      }
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
    String workCity = locationDao.getCityCode(personInfoDto.getWorkCity(), null);
    companyUserInfo1.setCompanyUserId(companyUserId)
      .setJobNum(personInfoDto.getJobNum())
      .setEmployeeType(personInfoDto.getEmployeeType())
      .setDepartment(personInfoDto.getDepartment())
      .setStation(personInfoDto.getStation())
      .setWorkCity(workCity)
      .setBank(personInfoDto.getBank())
      .setBankNum(personInfoDto.getBankNum())
      .setSocialNum(personInfoDto.getSocialNum())
      .setFundNum(personInfoDto.getFundNum())
      .setEmail(personInfoDto.getEmail())
      .setRemark(personInfoDto.getRemark());
    if (StrUtil.isNotEmpty(personInfoDto.getEducation())){
      if (EnumUtil.getEnumByName(EducationEnum.class, personInfoDto.getEducation()) != null){
        companyUserInfo1.setEducation(EnumUtil.getEnumByName(EducationEnum.class, personInfoDto.getEducation()).getCode());
      } else {
        throw new LogicException("学历填写有误！");
      }
    }
    if (StrUtil.isNotEmpty(personInfoDto.getPersonState())){
      if (EnumUtil.getEnumByName(PersonStateEnum.class, personInfoDto.getPersonState()) != null){
        companyUserInfo1.setPersonState(EnumUtil.getEnumByName(PersonStateEnum.class, personInfoDto.getPersonState()).getCode());
      } else {
        throw new LogicException("人员状态填写有误！");
      }
    } else {
      throw new LogicException("人员状态为必填项！");
    }
    if (StrUtil.isNotEmpty(personInfoDto.getEmployee())){
      if (EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getEmployee()) != null){
        companyUserInfo1.setEmployee(EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getEmployee()).getCode());
      } else {
        throw new LogicException("是否雇员填写有误！");
      }
    } else{
      throw new LogicException("是否雇员为必填项！");
    }
    if (StrUtil.isNotEmpty(personInfoDto.getHireDate())) {
        companyUserInfo1.setHireDate(TimeUtil.parseString(personInfoDto.getHireDate()));
    }
    if (StrUtil.isNotEmpty(personInfoDto.getTermDate())) {
      companyUserInfo1.setTermDate(TimeUtil.parseString(personInfoDto.getTermDate()));
    }
    if (StrUtil.isNotEmpty(personInfoDto.getMaritalStatus())){
      if (EnumUtil.getEnumByName(MaritalStatusEnum.class, personInfoDto.getMaritalStatus()) != null){
        companyUserInfo1.setMaritalStatus(EnumUtil.getEnumByName(MaritalStatusEnum.class, personInfoDto.getMaritalStatus()).getCode());
      } else {
        throw new LogicException("婚姻状况填写有误！");
      }
    }
    if (StrUtil.isNotEmpty(personInfoDto.getIntroduceTalents())){
      if (EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getIntroduceTalents()) != null){
        companyUserInfo1.setIntroduceTalents(EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getIntroduceTalents()).getCode());
      } else {
        throw new LogicException("是否引进人才填写有误！");
      }
    }
    if (StrUtil.isNotEmpty(personInfoDto.getSpecialIndustry())){
      if (EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getSpecialIndustry()) != null){
        companyUserInfo1.setSpecialIndustry(EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getSpecialIndustry()).getCode());
      } else {
        throw new LogicException("是否特定行业填写有误！");
      }
    }
    if (StrUtil.isNotEmpty(personInfoDto.getIsInvestor())){
      if (EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getIsInvestor()) != null){
        companyUserInfo1.setIsInvestor(EnumUtil.getEnumByName(YesOrNoEnum.class, personInfoDto.getIsInvestor()).getCode());
      } else {
        throw new LogicException("是否股东、投资人填写有误！");
      }
    }
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
    if(companyUserInfo != null) {
      companyUserInfoMapper.updateById(companyUserInfo1);
    } else {
      companyUserInfoMapper.insert(companyUserInfo1);
    }
  }

  public String exportAttendanceExcel(HttpServletResponse response, AttendanceModel attendanceDto) {

    return "成功！";
  }

  public String exportZip(HttpServletResponse response, NormalSalaryForm normalSalaryForm) throws IOException, ParseException {
    String uuid = RandomUtil.simpleUUID();
    exportPersonInfoExcel(response, normalSalaryForm.getCompanyId(), uuid);
    exportNormalSalary(response ,normalSalaryForm, uuid);
    File file = ZipUtil.zip(appInfo.getFilePath()+ Constant.ZIP + uuid);
    response.reset();
    response.setContentType("multipart/form-data");
    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("压缩.zip","UTF-8"));
    OutputStream output = new FileOutputStream(file);
    output.close();
    return "成功！";
  }
}
