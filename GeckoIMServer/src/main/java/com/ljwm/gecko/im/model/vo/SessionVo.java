package com.ljwm.gecko.im.model.vo;

import com.ljwm.gecko.base.entity.CustomerMessage;
import com.ljwm.gecko.base.entity.CustomerSession;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Author: xixil
 * Date: 2018/10/10 11:22
 * RUA
 */

@Data
@Accessors(chain = true)
public class SessionVo extends CustomerSession{

  private List<CustomerMessage> customerMessages;
}
