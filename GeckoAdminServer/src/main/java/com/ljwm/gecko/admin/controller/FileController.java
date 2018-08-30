package com.ljwm.gecko.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.utils.FileKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("file")
@Api(tags = "上传接口")
public class FileController extends BaseController {

  @Autowired
  private AppInfo appInfo;

  @PostMapping("upload")
  @ApiOperation(value = "后台--全局上传接口，必传参数 file")
  public Result<String> upload(@RequestParam("file") MultipartFile file) {
    String fileName = FileKit.saveUploadFile(file, appInfo.getFilePath(), appInfo.getPic());
    if (StrUtil.isEmpty(fileName))
      return fail(ResultEnum.FAIL_TO_SAVE_FILE);
    return success(fileName);
  }
}