package com.ljwm.gecko.base.exception;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
@Order(-1)
public class AssertExceptionAspect {

  @org.springframework.web.bind.annotation.ExceptionHandler(value = AssertionError.class)
  @ResponseBody
  public Result handleAssertionError(AssertionError e) {
    log.info("AssertionError occur: {}", e);
    return Result.fail(ResultEnum.DATA_ERROR.getCode(), ResultEnum.DATA_ERROR.getMsg() + ":" + e.getMessage());
  }

}
