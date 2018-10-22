package com.ljwm.gecko.client.delay;

import com.ljwm.gecko.base.enums.DelayActionEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Janiffy
 * @date 2018/10/19 13:40
 */
@Slf4j
public class Delay implements Delayed {


  private Long orderId;

  private long time;

  private long n;

  private DelayActionEnum delayActionEnum;

  @Override
  public long getDelay(TimeUnit unit) {
    return 0;
  }

  @Override
  public int compareTo(Delayed o) {
    return 0;
  }
}
