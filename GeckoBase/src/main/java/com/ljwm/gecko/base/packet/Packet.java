package com.ljwm.gecko.base.packet;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Packet  Created by yunqisong on 2018/9/22.
 * FOR: 数据包封装
 */

@Slf4j
@SuppressWarnings("ALL")
@Data
@Accessors(chain = true)
public class Packet<T> {

  @ApiModelProperty("ID_WORK 策略生成")
  private String id;

  @ApiModelProperty("发送主题")
  private String topic;

  @ApiModelProperty("发送时间")
  private Date createTime;

  @ApiModelProperty("报文")
  private T message;

  /**
   * 通过主题封包
   *
   * @param topic
   * @param t
   * @param <T>
   * @return
   */
  public static <T> Packet create(String topic, T t) {

    return new Packet()
      .setCreateTime(DateTime.now())
      .setId(IdWorker.getIdStr())
      .setTopic(topic)
      .setMessage(t)
      ;
  }

}

