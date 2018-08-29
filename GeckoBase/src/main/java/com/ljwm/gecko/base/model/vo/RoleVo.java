package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.entity.Role;
import com.ljwm.gecko.base.model.dto.FunctionDto;
import lombok.Data;

import java.util.List;

@Data
public class RoleVo extends Role {

  private Boolean deleteAble;

  private List<Function> functions;
}
