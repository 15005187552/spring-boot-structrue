package com.ljwm.gecko.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.admin.model.form.FileTemplateQuery;
import com.ljwm.gecko.admin.service.FileService;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.dto.FileTemplateDto;
import com.ljwm.gecko.base.model.vo.FileTemplateVo;
import com.ljwm.gecko.base.utils.FileKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("fileTemplate")
@Api(tags = "文件上传和下载接口")
public class FileTemplateController  extends BaseController {
  public static final String FILE_TEMPLATE = "excelTemplate";

  @Autowired
  private FileService fileService;

  @Autowired
  private AppInfo appInfo;

  @PostMapping("upload")
  @ApiOperation(value = "后台--模板文件上传接口，必传参数 file")
  public Result<String> upload(@RequestParam("file") MultipartFile file) {
    String fileName = FileKit.saveUploadFile(file, appInfo.getFilePath(), FILE_TEMPLATE);
    if (StrUtil.isEmpty(fileName))
      return fail(ResultEnum.FAIL_TO_SAVE_FILE);
    return success(fileName);
  }

  @PostMapping("save")
  @ApiOperation("保存excel模板")
  public Result save(@RequestBody FileTemplateDto fileTemplateDto) {
    fileTemplateDto.setCreatorId(SecurityKit.currentId());
      fileService.saveTemplateFile(fileTemplateDto);
      return success();
  }

  @PostMapping("find")
  @ApiOperation("上传完成后显示上传文件信息")
  public Result<Page<FileTemplateVo>> find(@RequestBody FileTemplateQuery query) {
    return success(fileService.find(query)) ;
  }
}
