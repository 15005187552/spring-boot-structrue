package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Janiffy
 * @date 2018/9/10 15:05
 */
@Slf4j
@RequestMapping("/excel")
@RestController
public class ExcelController {

  @Autowired
  ExcelService excelService;

  @PostMapping("/personInfo/import")
  public Result importExcel(@RequestParam("file") MultipartFile file, @RequestParam("companyId")Long companyId) throws Exception {
    excelService.improtPersonInfo(file, companyId);
    return Result.success("导入成功！");
  }
}
