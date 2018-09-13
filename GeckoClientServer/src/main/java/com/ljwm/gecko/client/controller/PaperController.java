package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.Paper;
import com.ljwm.gecko.base.service.PaperCommonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController extends BaseController {

  @Autowired
  private PaperCommonService paperCommonService;


  @GetMapping("find")
  @ApiOperation("查询资质列表")
  public Result<List<Paper>> find(){
    return success(paperCommonService.getAll());
  }
}
