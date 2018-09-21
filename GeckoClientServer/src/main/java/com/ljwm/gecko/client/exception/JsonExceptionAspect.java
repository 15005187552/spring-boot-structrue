package com.ljwm.gecko.client.exception;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.gecko.base.utils.UtilKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Janiffy
 * @date 2018/9/21 17:13
 */
@Slf4j
@ControllerAdvice
@Order(-1)
public class JsonExceptionAspect {

  @org.springframework.web.bind.annotation.ExceptionHandler(value = HttpMessageNotReadableException.class)
  @ResponseBody
  public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    log.warn("Logic Exception occur: {}", e.getMessage());
    String detailMessage = UtilKit.getTargetByExpression("detailMessage",e);
    return Result.fail(ResultEnum.DATA_ERROR.getCode(), "提交的JSON数据"+detailMessage.split("\"")[1]+"不满足指定的JSON格式请校验后再提交!");
  }

}
