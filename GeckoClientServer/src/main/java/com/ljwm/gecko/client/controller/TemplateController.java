package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Janiffy
 * @date 2018/9/12 12:28
 */
@RestController
@RequestMapping("/template")
@Api(tags = "模板 API")
public class TemplateController {

  @Autowired
  ApplicationInfo appInfo;


  @PostMapping("/downloadEmployee")
  public Result downloadEmployee(HttpServletResponse response, String filePath) throws IOException {
    // 下载网络文件
    int bytesum = 0;
    int byteread = 0;

    URL url = new URL(appInfo.getWebPath()+"template/员工信息表.xlsx");

    try {
      URLConnection conn = url.openConnection();
      InputStream inStream = conn.getInputStream();
      FileOutputStream fs = new FileOutputStream(filePath+"员工信息表.xlsx");

      byte[] buffer = new byte[1204];
      int length;
      while ((byteread = inStream.read(buffer)) != -1) {
        bytesum += byteread;
        System.out.println(bytesum);
        fs.write(buffer, 0, byteread);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return Result.success("下载完成");
  }
}
