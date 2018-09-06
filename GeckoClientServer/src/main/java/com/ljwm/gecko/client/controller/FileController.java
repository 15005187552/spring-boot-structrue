package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Janiffy
 * @date 2018/9/4 15:13
 */
@RestController
@Api(tags = "文件相关 API")
public class FileController {
  @Autowired
  FileService fileService;

  @PostMapping("/uploadfile")
  @ApiOperation("上传文件")
  public Result commit(@RequestParam("file") MultipartFile file){
    return fileService.upload(file);
  }


}
