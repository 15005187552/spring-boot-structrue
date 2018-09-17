package com.ljwm.gecko.base.aspect;

import cn.hutool.core.date.DateUtil;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.Order;
import com.ljwm.gecko.base.entity.OrderLog;
import com.ljwm.gecko.base.enums.OrderLoggerActionEnum;
import com.ljwm.gecko.base.enums.OrderStatusEnum;
import com.ljwm.gecko.base.mapper.OrderLogMapper;
import com.ljwm.gecko.base.model.vo.ResultMe;
import com.ljwm.gecko.base.service.OrderService;
import com.ljwm.gecko.base.utils.UtilKit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ljwm.gecko.base.annotation.*;

import java.util.Map;

@Slf4j
@Aspect
@Component
@SuppressWarnings({"unchecked"})
public class OrderLogAspect {

  @Autowired
  private OrderService orderService;

  @Autowired
  private CommonService commonService;

  @Autowired
  private OrderLogMapper orderLogMapper;

  @AfterReturning(pointcut = "@annotation(com.ljwm.gecko.base.annotation.OrderLogger)", returning = "retVal")
  public void log(JoinPoint pjp, Object retVal) throws Throwable {
    // 1. 验证日志是否记录
    if (retVal == null) return;  // 空对象不记录日志
    log.info("日志切面拿到的对象为：{}", retVal);
    Order order = null;
    Integer state = null;
    if (retVal instanceof Order) {
      order = (Order) retVal;
    } else if (retVal instanceof Map) {
      order = (Order) ((Map) retVal).get("order");
      state = (Integer) ((Map) retVal).get("action");
    }
    assert order != null;
    state = state == null ? order.getStatus() : state;
    log.info("订单状态:{} 名称:{}", state, OrderStatusEnum.codeOf(state));
    // 2. 通过表达式取值从不同类型的JavaBean中把指定键的值取出来
    //Long orderId = UtilKit.getTargetByExpression("id", retVal);
    String num = UtilKit.getTargetByExpression("orderNo", retVal);
    Integer status = UtilKit.getTargetByExpression("status", retVal);

    // 3. 验证是系统还是人工操作
    ResultMe resultMe = SecurityKit.currentUser();

    // 4. 构造通用日志对象
    OrderLog orderLog = new OrderLog()
      .setCreateTime(DateUtil.date())
      .setToStatus(status)
      .setOrderId(num)
      .setOperator(resultMe == null ? "系统自动" : resultMe.getUserName())
      .setOperatorId(resultMe == null ? null : resultMe.getId());

    // 5. 分析注解中的枚举类型，进行日志分发
    switch (state) {
      case 5: { // 下单事件
        orderLog.setText("订单:" + num + ",下单成功,待服务商定价。");
        break;
      }
      case 10: { // 下单事件
        orderLog.setText("订单:" + num + ",下单成功,待支付。");
        break;
      }
      case 20: {
        orderLog.setText("订单:" + num + ",支付成功。");
        break;
      }
      case 50: {
        orderLog.setText("订单:" + num + ",交易成功。");
        break;
      }
      case 60: {
        orderLog.setText("订单:" + num + ",关闭。");
        break;
      }
    }

    // 6. 日志写进数据库
    orderLogMapper.insert(orderLog);
  }
}
