package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.NoticeQuery;
import com.ljwm.gecko.admin.model.form.NoticeSaveForm;
import com.ljwm.gecko.admin.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Author: xixil
 * Date: 2018/10/10 10:38
 * RUA
 */

@RestController
@RequestMapping("notice")
@Api(tags = "公告管理 API")
public class NoticeController extends BaseController {

  @Autowired
  private NoticeService noticeService;

  @PostMapping("save")
  @ApiOperation("公告保存")
  public Result save(@RequestBody NoticeSaveForm form) {
    return success(noticeService.save(form));
  }

  @PostMapping("find")
  @ApiOperation("公告查询")
  public Result find(@RequestBody NoticeQuery query) {
    return success(noticeService.find(query));
  }

  @PostMapping("disabled/{id}")
  @ApiOperation("公告启用禁用")
  public Result disabled(@PathVariable @Valid Long id) {
    return success(noticeService.disabled(id));
  }

  @PostMapping("delete/{id}")
  @ApiOperation("公告删除")
  public void delete(@PathVariable @Valid Long id) {
    noticeService.delete(id);
  }
}
