package com.ljwm.gecko.base.delay;

import cn.hutool.core.date.DateUtil;
import com.ljwm.gecko.base.enums.DelayActionEnum;
import com.ljwm.gecko.base.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Janiffy
 * @date 2018/10/19 13:40
 */
@Slf4j
public class DelayEvent implements Delayed {


  private Long orderId;

  private long time;

  private long n;

  private DelayActionEnum delayActionEnum;

  /**
   * 原子类
   */
  private static final AtomicLong atomic = new AtomicLong(0);


  /**
   * orderId:订单id
   * timeout：触发的时间 秒
   */
  public DelayEvent(Long orderId, Long timeout, Integer actionCode) {
    this.orderId = orderId;
    this.time = System.nanoTime() + TimeUnit.NANOSECONDS.convert(timeout, TimeUnit.SECONDS);
    this.n = atomic.getAndIncrement();
    this.delayActionEnum = EnumUtil.getEnumBycode(DelayActionEnum.class, actionCode);

    log.info("任务执行的时间: {}", DateUtil.date(TimeUnit.MILLISECONDS.convert(this.time, TimeUnit.NANOSECONDS)));
  }
  @Override
  public long getDelay(TimeUnit unit) {
    return unit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);
  }

  @Override
  public int compareTo(Delayed other) {
    if (other == this)
      return 0;
    if (other instanceof DelayEvent) {
      DelayEvent x = (DelayEvent) other;
      long diff = time - x.time;
      if (diff < 0)
        return -1;
      else if (diff > 0)
        return 1;
      else if (n < x.n)
        return -1;
      else
        return 1;
    }
    long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
    return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (orderId ^ (orderId >>> 32));
    result = prime * result + (int) (time ^ (time >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DelayEvent other = (DelayEvent) obj;
    if (!orderId.equals(other.orderId))
      return false;
    return Long.valueOf(time).equals(other.time);
  }
}
