package com.ljwm.gecko.base.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RoleDto extends Role {

  @JSONField(serialize = false)
  private List<FunctionDto> functions;
}
