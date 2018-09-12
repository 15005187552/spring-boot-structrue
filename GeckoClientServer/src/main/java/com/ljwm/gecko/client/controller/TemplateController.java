package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author Janiffy
 * @date 2018/9/12 12:28
 */
@RestController
@RequestMapping("/template")
@Api(tags = "模板 API")
@Slf4j
public class TemplateController {

  @Autowired
  ApplicationInfo appInfo;


  @PostMapping("/downloadEmployee")
  public Result downloadEmployee(HttpServletResponse response) throws IOException {
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
