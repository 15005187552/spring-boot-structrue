package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.security.JwtUser;
import com.ljwm.gecko.client.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Janiffy
 * @date 2018/9/4 15:13
 */
@RestController
@Api(tags = "文件相关 API")
public class FileController extends BaseController {
  @Autowired
  FileService fileService;

  @Autowired
  private AppInfo appInfo;

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("/uploadfile")
  @ApiOperation("上传文件")
  public Result commit(@RequestParam("file") MultipartFile file){
    return fileService.upload(file);
  }

  @PreAuthorize(JwtUser.HAS_MEMEBER_ROLE)
  @PostMapping("delete")
  @ApiOperation("删除文件")
  public Result delete(@RequestBody String id){
    String srcPath = appInfo.getFilePath() + Constant.CACHE + id;
    Fileutil.deleteGeneralFile(srcPath);
    return success();
  }


}
