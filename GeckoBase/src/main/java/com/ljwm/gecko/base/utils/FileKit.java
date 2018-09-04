package com.ljwm.gecko.base.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * stoa Created by yunqisong on 2018/7/9/009.
 * FOR:
 */
@Data
@Slf4j
@Accessors(chain = true)
@SuppressWarnings("ALL")
@EqualsAndHashCode(callSuper = false)
public class FileKit {

  /**
   * 文件上传
   *
   * @param file
   * @param uploadPath
   * @param folder
   * @return
   */
  public static String saveUploadFile(MultipartFile file, String uploadPath, String folder) {
    String fileName = null;
    if (!file.isEmpty()) {
      fileName = file.getOriginalFilename();
      log.debug("上传的文件名为：" + fileName);
      String suffixName = fileName.substring(fileName.lastIndexOf("."));
      log.debug("上传的后缀名为：" + suffixName);
      fileName = folder + "/" + RandomUtil.randomUUID() + suffixName;
      java.io.File dest = new java.io.File(uploadPath + fileName);
      if (!dest.getParentFile().exists()) {
        log.debug("Create folder: {}", folder);
        dest.getParentFile().mkdirs();
      }
      try {
        file.transferTo(dest);
      } catch (Exception e) {
        log.error("Fail to save file {} ", dest.getAbsolutePath(), e);
        return null;
      }
    }
    return fileName;
  }


  /**
   * 从ClassPath下开始读取文件
   *
   * @param filePath
   * @return
   */
  public static String getStrFormClassPathFile(String filePath) {
    return FileUtil.readString(URLUtil.url(URLUtil.CLASSPATH_URL_PREFIX + filePath), "UTF-8");
  }

  /**
   * 下载文件
   *
   * @param response
   * @param inputStream
   */
  public static void downloadFile(HttpServletResponse response, InputStream inputStream, String name) {
    response.setContentType("application/force-download");// 设置强制下载不打开
//        response.addHeader("Content-Disposition",
//                String.format("attachment;fileName=%s", new String(name.getBytes(), Charset.forName("GBK"))));    // 设置文件名
    OutputStream ops = null;
    try {
      ops = response.getOutputStream();
//            StrUtil.
      ops.write(cn.hutool.core.io.IoUtil.readBytes(inputStream));
      ops.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (ops != null)
          ops.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


//  /**
//   * 从ClassPath下面下载
//   *
//   * @param fileName
//   * @param path
//   * @param response
//   */
//  public static void downloadInClassPath(String fileName, String path, HttpServletResponse response) {
//    try {
//      response.setContentType("multipart/form-data");
//      response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
//      IoUtil.copy(FlowController.class.getClassLoader().getResourceAsStream(path), response.getOutputStream());
//      response.getOutputStream().flush();
//    } catch (Exception e) {
//      log.info("IO ERROR -- E : {}", e);
//      throw new LogicException(ResultEnum.DATA_ERROR, "下载文件出错!");
//    } finally {
//      try {
//        response.getOutputStream().close();
//      } catch (IOException e) {
//        log.info("IO ERROR -- E : {}", e);
//        throw new LogicException(ResultEnum.DATA_ERROR, "下载文件出错!");
//      }
//    }
//  }
}
