package com.ljwm.gecko.provider.model.form;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RoleSaveForm {

  private Long id;

  private String roleName;

  private String roleDesc;

  private String roleCode;

  private List<Integer> functionIds;
}
