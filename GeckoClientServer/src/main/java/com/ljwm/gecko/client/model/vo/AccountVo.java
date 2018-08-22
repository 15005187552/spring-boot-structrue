package com.ljwm.gecko.client.model.vo;

import com.ljwm.gecko.base.entity.MemberAccount;
import com.ljwm.gecko.base.entity.MemberPassword;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by yuzhou on 2018/8/22.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AccountVo extends MemberAccount {

  private MemberPassword password;
}
