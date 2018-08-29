package com.ljwm.gecko.admin.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.enums.DeleteEnum;
import com.ljwm.gecko.admin.model.form.FunctionSaveForm;
import com.ljwm.gecko.admin.model.form.RoleQuery;
import com.ljwm.gecko.admin.model.form.RoleSaveForm;
import com.ljwm.gecko.admin.service.AuthService;
import com.ljwm.gecko.admin.service.FunctionService;
import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.entity.Role;
import com.ljwm.gecko.base.model.vo.FunctionTree;
import com.ljwm.gecko.base.model.vo.RoleVo;
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

  @PostMapping("findRole")
  @ApiOperation("分页查询角色")
  public Result<RoleVo> findRole(@RequestBody RoleQuery query) {
    return success(authService.findRole(query));
  }

  @GetMapping("getRole")
  @ApiOperation("获取角色")
  public Result<List<Role>> getRole() {
    return success(authService.getRole());
  }

  @GetMapping("roleDisabled")
  @ApiOperation("角色 禁用/启用")
  public Result roleDisabled(@RequestParam String id) {
    authService.roleDisabled(id);
    return success();
  }

  @GetMapping("roleDelete")
  @ApiOperation("角色 删除")
  public Result roleDelete(@RequestParam String id) {
    authService.roleDelete(id, DeleteEnum.NORMAL.getInfo());
    return success();
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

  @GetMapping("getFunction")
  @ApiOperation("获取菜单")
  public Result<List<Function>> getFunction() {
    return success(functionService.getFunction());
  }

  @GetMapping("funDisabled")
  @ApiOperation("菜单 禁用/启用")
  public Result funDisabled(@RequestParam String id) {
    functionService.funDisabled(id);
    return success();
  }

  @GetMapping("funDelete")
  @ApiOperation("菜单 删除")
  public Result funDelete(@RequestParam String id) {
    functionService.funDelete(id, DeleteEnum.NORMAL.getInfo());
    return success();
  }

  @GetMapping("funForceDelete")
  @ApiOperation("菜单 强制删除")
  public Result funForceDelete(@RequestParam String id) {
    functionService.funDelete(id, DeleteEnum.FORCE.getInfo());
    return success();
  }
}
