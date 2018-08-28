package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.model.dto.RoleDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Wolverine Created by yunqisong on 2018-4-5.
 * FOR: 返回前台用户Me信息
 */
@Data
@Accessors(chain = true)
public class ResultMe {

  private String id;

  private String token;

  private String userName;

  private String nickName;

//  private List<RoleDto> roles;
}
