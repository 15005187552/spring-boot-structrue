package com.ljwm.gecko.client.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.entity.*;
import com.ljwm.gecko.base.enums.CertificateType;
import com.ljwm.gecko.base.enums.TableNameEnum;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.utils.EnumUtil;
import com.ljwm.gecko.base.utils.excelutil.ExcelUtil;
import com.ljwm.gecko.client.model.dto.CompanyDto;
import com.ljwm.gecko.client.model.dto.PersonInfoDto;
import com.ljwm.gecko.client.model.dto.TemplateDto;
import com.ljwm.gecko.client.model.dto.TemplateForm;
import com.ljwm.gecko.client.model.vo.AttendanceExcelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/20 18:47
 */
@Slf4j
@Service
public class TemplateService {

  @Autowired
  ApplicationInfo appInfo;

  @Autowired
  TemplateMapper templateMapper;

  @Autowired
  AttributeMapper attributeMapper;

  @Autowired
  CommonService commonService;

  @Autowired
  AttendanceTemplateMapper attendanceTemplateMapper;

  @Autowired
  NaturalPersonMapper naturalPersonMapper;

  @Autowired
  CompanyUserInfoMapper companyUserInfoMapper;

  @Autowired
  MemberMapper memberMapper;

  String[] employeeStr = {"工号", "*姓名", "*证照类型", "*证照号码", "*国籍(地区)", "*性别", "*出生日期", "学历", "*人员状态", "*是否雇员",
    "*手机号码", "任职受雇日期", "员工类别", "部门", "岗位", "离职日期", "工作城市", "婚姻状况", "是否引进人才", "开户银行",
    "工资账号", "社保账号", "公积金账号", "是否特定行业", "是否股东、投资者", "是否残疾", "是否烈属", "是否孤老", "残疾证号", "烈属证号",
    "电子邮箱", "居住省份", "居住城市", "居住所在区", "居住详细地址", "备注"};

  String[] str = {"*姓名", "*证照类型", "*证照号码", "*社保基数", "*公积金基数", "*公积金比例"};
  @Transactional
  public Result uploadTemplate(TemplateForm templateForm) {
    List<TemplateDto> list = templateForm.getList();
    for(TemplateDto templateDto : list){
      Template template = new Template();
      template.setSort(templateDto.getSort()).setCompanyId(templateForm.getCompanyId()).setAttributeId(templateDto.getId());
      Template templateFind = templateMapper.selectOne(new QueryWrapper<Template>()
        .eq(Template.COMPANY_ID, template.getCompanyId()).eq(Template.ATTRIBUTE_ID, template.getAttributeId()));
      if (templateFind != null){
        template.setId(templateFind.getId());
        templateMapper.updateById(template);
      } else {
        templateMapper.insert(template);
      }
    }
    return Result.success("模板上传成功！");
  }

  public Result downloadEmployee(HttpServletResponse response) throws MalformedURLException {
    // 下载网络文件
    int bytesum = 0;
    int byteread = 0;
    String fileName = appInfo.getWebPath()+"template/员工信息表.xls";
    URL url = new URL(fileName);

    try {
      URLConnection conn = url.openConnection();
      InputStream inStream = conn.getInputStream();
      // 设置输出的格式
      response.reset();
      response.setContentType("multipart/form-data");
      response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName,"UTF-8"));
      // 循环取出流中的数据
      byte[] b = new byte[100];
      int len;
      while ((len = inStream.read(b)) > 0)
        response.getOutputStream().write(b, 0, len);
      inStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Result.success("下载完成");
  }

  public Result downloadTemplate(HttpServletResponse response) throws IOException {
    Map<String, String> map = new LinkedHashMap<>();
    int i = 0;
    for (String string : employeeStr){
      map.put(String.valueOf(i), string);
      i++;
    }
    response.reset();
    response.setContentType("multipart/form-data");
    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("模板表.xls","UTF-8"));
    OutputStream output = response.getOutputStream();
    ExcelUtil.exportExcel(map, null, output);
    output.close();
    return Result.success("导出成功！");
  }

  public Result getEmployeeTem() {
    Map<String, String> map = new LinkedHashMap<>();
    Field[] fields = PersonInfoDto.class.getDeclaredFields();
    int i = 0;
    for (String string : employeeStr){
      map.put(fields[i].getName(), string);
      i++;
    }
    return Result.success(map);
  }

  @Transactional
  public Result uploadAttendanceTem(TemplateForm templateForm) {
    List<TemplateDto> list = templateForm.getList();
    for(TemplateDto templateDto : list){
      AttendanceTemplate attendanceTemplate = new AttendanceTemplate();
      attendanceTemplate.setSort(templateDto.getSort()).setCompanyId(templateForm.getCompanyId()).setAttributeId(templateDto.getId());
      AttendanceTemplate templateFind = attendanceTemplateMapper.selectOne(new QueryWrapper<AttendanceTemplate>()
        .eq(AttendanceTemplate.COMPANY_ID, attendanceTemplate.getCompanyId()).eq(AttendanceTemplate.ATTRIBUTE_ID, attendanceTemplate.getAttributeId()));
      if (templateFind != null){
        attendanceTemplate.setId(templateFind.getId());
        attendanceTemplateMapper.updateById(attendanceTemplate);
      } else {
        attendanceTemplateMapper.insert(attendanceTemplate);
      }
    }
    return Result.success("模板上传成功！");
  }

  public Result getAttendanceTem(CompanyDto companyDto) {
    List<Template> list = templateMapper.selectList(new QueryWrapper<Template>()
      .eq(Template.COMPANY_ID, companyDto.getCompanyId())
      .orderByAsc(true,Template.SORT));
    JSONObject map = new JSONObject(true);
    int i = -1000;
    for (String string : str){
      map.put(String.valueOf(i), string);
      i++;
    }
    List<Attribute> attributeList = attributeMapper.selectList(new QueryWrapper<Attribute>().eq(Attribute.TABLE_NAME, TableNameEnum.T_ADD_SPECIAL.getCode()));
    for (Attribute attribute : attributeList){
      map.put(String.valueOf(attribute.getId()), attribute.getName());
    }
    for (Template template : list) {
      map.put(String.valueOf(template.getAttributeId()), attributeMapper.selectById(template.getAttributeId()).getName());
    }
    log.info("map : {}", map.toJSONString());
    return Result.success(map);
  }

  public Result downloadAttendance(HttpServletResponse response, CompanyDto companyDto) throws IOException {
    List<Template> list = templateMapper.selectList(new QueryWrapper<Template>()
      .eq(Template.COMPANY_ID, companyDto.getCompanyId())
      .orderByAsc(true,Template.SORT));
    Map<String, String> map = new LinkedHashMap<>();
    int i = -1000;
    for (String string : str){
      map.put(String.valueOf(i), string);
      i++;
    }
    List<Attribute> attributeList = attributeMapper.selectList(new QueryWrapper<Attribute>().eq(Attribute.TABLE_NAME, TableNameEnum.T_ADD_SPECIAL.getCode()));
    for (Attribute attribute : attributeList){
      map.put(String.valueOf(attribute.getId()), attribute.getName());
    }
    for (Template template : list) {
      map.put(String.valueOf(template.getAttributeId()), attributeMapper.selectById(template.getAttributeId()).getName());
    }
    List<AttendanceExcelVo> dataList = new ArrayList();
    List<NaturalPerson> naturalPersonList = naturalPersonMapper.selectList(new QueryWrapper<NaturalPerson>().eq(NaturalPerson.COMPANY_ID, companyDto.getCompanyId()));
    for (NaturalPerson naturalPerson : naturalPersonList){
      AttendanceExcelVo attendanceExcelVo = new AttendanceExcelVo();
      attendanceExcelVo.setCertificate(EnumUtil.getNameBycode(CertificateType.class,naturalPerson.getCertificate()))
        .setCertNum(naturalPerson.getCertNum()).setName(naturalPerson.getName());
      List<CompanyUserInfo> companyUserList = companyUserInfoMapper.selectCompanyUser(companyDto.getCompanyId(), naturalPerson.getMemberId());
      if (CollectionUtil.isNotEmpty(companyUserList)){
        CompanyUserInfo companyUserInfo = companyUserList.get(0);
        if(companyUserInfo.getFundBase() != null){
          attendanceExcelVo.setFundBase(companyUserInfo.getFundBase().toString());
        }
        if (companyUserInfo.getFundPer() != null){
          attendanceExcelVo.setFundPer(companyUserInfo.getFundPer().toString());
        }
        if (companyUserInfo.getSocialBase() != null){
          attendanceExcelVo.setSocialBase(companyUserInfo.getSocialBase().toString());
        }
      }
      dataList.add(attendanceExcelVo);
    }
    response.reset();
    response.setContentType("multipart/form-data");
    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("模板表.xls","UTF-8"));
    OutputStream output = response.getOutputStream();
    ExcelUtil.exportExcel(map, dataList, output);
    output.close();
    return Result.success("导出成功！");
  }

}
