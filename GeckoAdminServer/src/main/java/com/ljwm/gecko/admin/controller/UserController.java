package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.admin.model.form.AdminQuery;
import com.ljwm.gecko.admin.model.form.AdminSaveForm;
import com.ljwm.gecko.admin.service.UserService;
import com.ljwm.gecko.base.model.vo.AdminVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@Api(tags = "用户管理 API")
public class UserController extends BaseController {


  @Autowired
  private UserService userService;

  // ================== 后管用户
  @PostMapping("saveAdmin")
  @ApiOperation("保存后管用户")
  public Result saveAdmin(@RequestBody AdminSaveForm form) {
    return success(userService.saveAdmin(form));
  }

//
//  @PostMapping("findAdmin")
//  @ApiOperation("分页查询后管用户")
//  public Result<Page<AdminVo>>  findAdmin(@RequestBody AdminQuery adminQuery) {
//    return success(userService.findAdmin(adminQuery));
//  }
}
