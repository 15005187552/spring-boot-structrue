package com.ljwm.gecko.provider.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class MessageBean {


  private Long fromId;

  private String message;
  private String name;

  private String subject;

  private Date createTime;
}
