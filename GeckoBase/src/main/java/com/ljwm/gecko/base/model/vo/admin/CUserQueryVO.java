package com.ljwm.gecko.base.model.vo.admin;

import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.entity.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Author: xixil
 * Date: 2018/10/23 15:53
 * RUA
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class CUserQueryVO extends CompanyUser{

  private AdminMemberVO member;
}
