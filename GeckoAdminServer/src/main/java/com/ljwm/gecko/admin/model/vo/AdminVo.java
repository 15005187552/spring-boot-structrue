package com.ljwm.gecko.admin.model.vo;

import com.ljwm.gecko.base.entity.Admin;
import com.ljwm.gecko.base.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AdminVo extends Admin {

  private List<Role> roles;
}
