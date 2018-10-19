package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.ProtocolForm;
import com.ljwm.gecko.admin.model.form.ProtocolQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: xixil
 * Date: 2018/10/18 18:40
 * RUA
 */

@RestController
@RequestMapping("protocol")
@Api(tags = "协议管理 API")
public class ProtocolController extends BaseController{

  @PostMapping("save")
  @ApiOperation("保存")
  public Result save(@RequestBody ProtocolForm form){
    return success();
  }

  @PostMapping("find")
  @ApiOperation("查询")
  public Result find(@RequestBody ProtocolQuery query){
    return success();
  }
}
