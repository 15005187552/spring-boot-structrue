package com.ljwm.gecko.base.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljwm.gecko.base.entity.Admin;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AdminDto extends Admin{



  private List<RoleDto> roles;
}
