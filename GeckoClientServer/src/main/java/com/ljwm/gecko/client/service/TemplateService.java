package com.ljwm.gecko.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.entity.Template;
import com.ljwm.gecko.base.mapper.AttributeMapper;
import com.ljwm.gecko.base.mapper.TemplateMapper;
import com.ljwm.gecko.base.utils.excelutil.ExcelUtil;
import com.ljwm.gecko.client.model.dto.CompanyDto;
import com.ljwm.gecko.client.model.dto.TemplateDto;
import com.ljwm.gecko.client.model.dto.TemplateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/20 18:47
 */
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

  public Result uploadTemplate(TemplateForm templateForm) {
    List<TemplateDto> list = templateForm.getList();
    for(TemplateDto templateDto : list){
      Template template = new Template();
      template.setSort(templateDto.getSort()).setCompanyId(templateForm.getCompanyId()).setAttributeId(templateDto.getId());
      commonService.insertOrUpdate(template, templateMapper);
    }
    return Result.success("模板上传成功！");
  }

  public Result downloadEmployee(HttpServletResponse response) throws MalformedURLException {
    // 下载网络文件
    int bytesum = 0;
    int byteread = 0;
    String fileName = appInfo.getWebPath()+"template/员工信息表.xlsx";
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

  public Result downloadTemplate(HttpServletResponse response, CompanyDto companyDto) throws IOException {
    List<Template> list = templateMapper.selectList(new QueryWrapper<Template>()
      .eq(Template.COMPANY_ID, companyDto.getCompanyId())
      .orderByAsc(true,Template.SORT));
    String[] str = {"工号", "*姓名", "*证照类型", "*证照号码", "*国籍(地区)", "*人员状态", "*是否雇员", "*手机号码", "社保缴费基数", "公积金缴费基数", "公积金缴费比例"};
    Map<String, String> map = new LinkedHashMap<>();
    int i = 0;
    for (String string : str){
      map.put(String.valueOf(i), string);
      i++;
    }
    for (Template template : list) {
      map.put(String.valueOf(i), attributeMapper.selectById(template.getId()).getName());
      i++;
    }
    response.reset();
    response.setContentType("multipart/form-data");
    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("模板表.xlsx","UTF-8"));
    OutputStream output = response.getOutputStream();
    ExcelUtil.exportExcel(map, null, output);
    output.close();
    return Result.success("导出成功！");
  }

  public Result getEmployeeTem(CompanyDto companyDto) {
    List<Template> list = templateMapper.selectList(new QueryWrapper<Template>()
      .eq(Template.COMPANY_ID, companyDto.getCompanyId())
      .orderByAsc(true,Template.SORT));
    String[] str = {"工号", "*姓名", "*证照类型", "*证照号码", "*国籍(地区)", "*人员状态", "*是否雇员", "*手机号码", "社保缴费基数", "公积金缴费基数", "公积金缴费比例"};
    Map<String, String> map = new LinkedHashMap<>();
    int i = 0;
    for (String string : str){
      map.put(String.valueOf(i), string);
      i++;
    }
    for (Template template : list) {
      map.put(String.valueOf(i), attributeMapper.selectById(template.getId()).getName());
      i++;
    }
    return Result.success(map);
  }
}
