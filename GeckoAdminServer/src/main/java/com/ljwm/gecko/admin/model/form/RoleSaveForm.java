package com.ljwm.gecko.admin.model.form;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RoleSaveForm {

  private String id;

  private String roleName;

  private List<Integer> functionIds;
}