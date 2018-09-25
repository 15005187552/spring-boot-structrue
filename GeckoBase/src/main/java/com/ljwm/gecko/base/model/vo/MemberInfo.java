package com.ljwm.gecko.base.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemberInfo  extends Member {

 MemberAccount account;

}
