package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.DictQueryForm;
import com.ljwm.gecko.admin.service.DictService;
import com.ljwm.gecko.base.model.vo.DictVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dict")
@Api(tags = "系统参数 API")
public class DictController extends BaseController {

  @Autowired
  private DictService dictService;

  @PostMapping("/findByPage")
  @ApiOperation("查询字典表-----带分页")
  public Result<Page<DictVo>> findByPage(@RequestBody DictQueryForm dictQueryForm){
    return success(dictService.findByPage(dictQueryForm));
  }
}
