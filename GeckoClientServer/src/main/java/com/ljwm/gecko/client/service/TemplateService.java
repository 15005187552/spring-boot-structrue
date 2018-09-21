package com.ljwm.gecko.client.service;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.entity.Template;
import com.ljwm.gecko.base.mapper.TemplateMapper;
import com.ljwm.gecko.client.model.dto.TemplateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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

  public Result uploadTemplate(TemplateForm templateForm) {
    int sort = 1;
    String[] name = templateForm.getName();
    for(String str : name){
      Template template = new Template();
      template.setSort(sort).setCompanyId(templateForm.getCompanyId()).setName(str);
      templateMapper.insert(template);
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
}
