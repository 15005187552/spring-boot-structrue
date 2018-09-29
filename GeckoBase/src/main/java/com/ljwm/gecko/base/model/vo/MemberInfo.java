package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemberInfo  extends Member {

 MemberAccount account;

}
