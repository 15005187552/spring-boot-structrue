package com.ljwm.gecko.client.controller;

import com.ljwm.gecko.base.utils.excelutil.ExcelLogs;
import com.ljwm.gecko.base.utils.excelutil.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/10 15:05
 */
@Slf4j
@RequestMapping("/excel")
@RestController
public class ExcelController {


  @PostMapping("/personInfo/import")
  public void importExcel(@RequestParam("file") MultipartFile file) throws IOException {
    InputStream inputStream = file.getInputStream();

    ExcelLogs logs =new ExcelLogs();
    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);
    for(Map m : importExcel){
     log.debug("{}", m);
    }
  }
}
