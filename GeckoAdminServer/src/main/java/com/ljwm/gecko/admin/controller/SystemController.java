package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.FunctionSaveForm;
import com.ljwm.gecko.admin.model.form.RoleSaveForm;
import com.ljwm.gecko.admin.service.AuthService;
import com.ljwm.gecko.admin.service.FunctionService;
import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.entity.Role;
import com.ljwm.gecko.base.model.vo.FunctionTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys")
@SuppressWarnings("all")
@Api(tags = "系统管理 API")
public class SystemController extends BaseController {


  @Autowired
  private FunctionService functionService;

  @Autowired
  private AuthService authService;

  // ================== 角色
  @PostMapping("saveRole")
  @ApiOperation("保存角色")
  public Result<Role> saveRole(@RequestBody RoleSaveForm form) {
    return success(authService.saveRole(form));
  }

  // ================== 权限
  @PostMapping("saveFunction")
  @ApiOperation("保存菜单")
  public Result<Function> saveFunction(@RequestBody FunctionSaveForm form) {
    return success(functionService.saveFunction(form));
  }

  @GetMapping("function")
  @ApiOperation("获取系统权限树")
  public Result<List<FunctionTree>> function(String text) {
    return success(functionService.tree(text));
  }
}
