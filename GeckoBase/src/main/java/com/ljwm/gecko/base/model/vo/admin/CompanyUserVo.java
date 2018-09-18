package com.ljwm.gecko.base.model.vo.admin;

import com.ljwm.gecko.base.entity.CompanyUser;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompanyUserVo extends CompanyUser {

  private Boolean memberDisabled;

  private String nickName;

  private String avatarPath;

  private String regMobile;

  private String memberIdcard;
}
