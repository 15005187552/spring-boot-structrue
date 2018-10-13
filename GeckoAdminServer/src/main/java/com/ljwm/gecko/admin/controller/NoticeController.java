package com.ljwm.gecko.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.NoticeQuery;
import com.ljwm.gecko.admin.model.form.NoticeSaveForm;
import com.ljwm.gecko.admin.service.NoticeService;
import com.ljwm.gecko.base.enums.EquipTypeEnum;
import com.ljwm.gecko.base.enums.TagEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;


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

  @GetMapping("disabled/{id}")
  @ApiOperation("公告启用禁用")
  public Result disabled(@PathVariable @Valid Long id) {
    return success(noticeService.disabled(id));
  }

  @GetMapping("delete/{id}")
  @ApiOperation("公告删除")
  public void delete(@PathVariable @Valid Long id) {
    noticeService.delete(id);
  }


  @GetMapping("getTagEnum")
  public Result getTagEnum(){
    return  success(EnumUtils.getEnumList(TagEnum.class).stream().map(i-> {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("value",i.getCode());
      jsonObject.put("label",i.getName());
      return jsonObject;
    }).collect(Collectors.toList()));
  }
}
