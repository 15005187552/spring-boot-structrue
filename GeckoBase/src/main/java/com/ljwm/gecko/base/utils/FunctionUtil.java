package com.ljwm.gecko.base.utils;

import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author Janiffy
 * @date 2018/8/30 9:32
 */
@Slf4j
public class FunctionUtil {
  /**
   * 尝试次数
   *
   * @param retryLimit
   * @param retryCallable
   * @return
   */
  public static boolean retryOnException(int retryLimit, Callable<Boolean> retryCallable) {

    Boolean result = null;

    for (int i = 0; i < retryLimit; i++) {
      try {
        result = retryCallable.call();
      } catch (Exception e) {
        log.error("Error when try on {} times", i + 1, e);
        if (i == retryLimit - 1) {
          if (e instanceof LogicException) throw (LogicException) e;
          else
            throw new LogicException(ResultEnum.DATA_ERROR, e.getMessage());
        }
      }
      if (result != null && result) {
        return true;
      } else if (i == retryLimit - 1) {
        log.error("After try {} times, it still fail", retryLimit);
      }
    }
    return false;
  }
}
