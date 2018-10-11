package com.ljwm.gecko.im.model.vo;

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
public class MessagesVo {

  private MineVo mine;

  private List<SessionVo> sessions;

  private List<PushMessageVo> pushMessages;
}
