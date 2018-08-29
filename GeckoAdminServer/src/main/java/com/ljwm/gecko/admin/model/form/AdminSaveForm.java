package com.ljwm.gecko.admin.model.form;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AdminSaveForm {

  private Long id;

  private String username;

  private String password;

  private String nickName;

  private List<Integer> roleIds;
}
