package com.ljwm.gecko.admin.util;

import cn.hutool.core.io.IoUtil;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.admin.controller.FileController;
import com.ljwm.gecko.admin.controller.LocationRateController;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@Data
@Slf4j
@Accessors(chain = true)
@SuppressWarnings("ALL")
@EqualsAndHashCode(callSuper = false)
public class FileKit extends com.ljwm.gecko.base.utils.FileKit {

  /**
   * 从ClassPath下面下载
   *
   * @param fileName
   * @param path
   * @param response
   */
  public static void downloadInClassPath(String fileName, String path, HttpServletResponse response) {
    try {
      response.setContentType("multipart/form-data");
      response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName,"UTF-8"));
      IoUtil.copy(LocationRateController.class.getClassLoader().getResourceAsStream(path), response.getOutputStream());
      response.getOutputStream().flush();
    } catch (Exception e) {
      log.info("IO ERROR -- E : {}", e);
      throw new LogicException(ResultEnum.DATA_ERROR, "下载文件出错!");
    } finally {
      try {
        response.getOutputStream().close();
      } catch (IOException e) {
        log.info("IO ERROR -- E : {}", e);
        throw new LogicException(ResultEnum.DATA_ERROR, "下载文件出错!");
      }
    }
  }
}
