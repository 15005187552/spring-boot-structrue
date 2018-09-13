package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.service.SpecialService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Janiffy
 * @date 2018/9/13 14:25
 */
@RestController
public class SpecialController {

  @Autowired
  SpecialService specialService;

  @PostMapping("/getSpecial")
  public Result getSpecial( @RequestParam("code") @ApiParam(value = "城市编码") String code) {
    return specialService.getSpecial(code);
  }
}
