package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.client.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Janiffy
 * @date 2018/9/20 18:06
 */
@RestController
public class AttributeController {

  @Autowired
  AttributeService attributeService;

  @RequestMapping(value = "/getAllAttribute", method = RequestMethod.GET)
  public Result getAllAttribute(){
    return  attributeService.getAllAttribute();
  }
}
