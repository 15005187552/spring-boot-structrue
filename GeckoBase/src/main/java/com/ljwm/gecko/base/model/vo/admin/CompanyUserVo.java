package com.ljwm.gecko.base.model.vo.admin;

import com.ljwm.gecko.base.entity.CompanyUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CompanyUserVo extends CompanyUser {

  private Boolean memberDisabled;

  private String nickName;

  private String avatarPath;

  private String regMobile;

  private String memberIdcard;
}
