package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.AdminQuery;
import com.ljwm.gecko.admin.model.form.AdminSaveForm;
import com.ljwm.gecko.admin.service.AdminService;
import com.ljwm.gecko.base.entity.Admin;
import com.ljwm.gecko.base.model.vo.AdminVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@SuppressWarnings("all")
@Api(tags = "用户管理 API")
public class UserController extends BaseController {


  @Autowired
  private AdminService adminService;

  // ================== 后管用户
  @PostMapping("saveAdmin")
  @ApiOperation("保存后管用户")
  public Result saveAdmin(@RequestBody AdminSaveForm form) {
    return success(adminService.saveAdmin(form));
  }


  @PostMapping("findAdmin")
  @ApiOperation("分页查询后管用户")
  public Result<Page<AdminVo>> findAdmin(@RequestBody AdminQuery adminQuery) {
    return success(adminService.findAdmin(adminQuery));
  }

  @GetMapping("getAdmin")
  @ApiOperation("获取后管用户")
  public Result<List<Admin>> getAdmin() {
    return success(adminService.getAdmin());
  }

//  @GetMapping("adminDisabled")
//  @ApiOperation("后管用户 禁用/启用")
//  public Result adminDisabled(@RequestParam String id){
//    userService.adminDisabled(id);
//    return success();
//  }

  @GetMapping("adminDelete")
  @ApiOperation("后管用户 删除")
  public Result adminDelete(@RequestParam String id) {
    adminService.adminDelete(id);
    return success();
  }

  @PostMapping("findMember")
  @ApiOperation("会员 查询")
  public Result findMember() {
    return success();
  }
}
