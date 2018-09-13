package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.enums.PaperTypeEnum;
import com.ljwm.gecko.admin.model.form.PaperQuery;
import com.ljwm.gecko.admin.model.form.PaperSaveForm;
import com.ljwm.gecko.admin.service.PaperService;
import com.ljwm.gecko.base.entity.Paper;
import com.ljwm.gecko.base.mapper.PaperMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("paper")
@SuppressWarnings("all")
@Api("证件管理 API")
public class PaperController extends BaseController {

  @Autowired
  private PaperService paperService;


  @PostMapping("save")
  @ApiOperation("保存")
  public Result<Paper> save(@RequestBody PaperSaveForm form) {
    form.setType(PaperTypeEnum.PERSON.getCode());
    return success(paperService.save(form));
  }

  @PostMapping("find")
  @ApiOperation("查询--分页")
  public Result find(@RequestBody PaperQuery query) {
    return success(paperService.find(query));
  }

  @GetMapping("delete/{id}")
  public Result delete(@PathVariable Integer id) {
    paperService.delete(id);
    return success();
  }
}
