package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.model.dto.PersonInfoForm;
import com.ljwm.gecko.client.service.PersonInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Janiffy
 * @date 2018/9/3 10:14
 */
@RestController
@RequestMapping("/person")
@Api(tags = "个人信息")
public class PersonInfoController {

  @Autowired
  PersonInfoService personInfoService;

  @PostMapping("/commitPersonInfo")
  @ApiOperation("提交个人信息")
  public Result commit(@RequestBody @Valid PersonInfoForm personInfoForm){
    return personInfoService.commit(personInfoForm);
  }

}
