package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 服务商员工表
 * </p>
 *
 * @author xixil
 * @since 2018-09-14
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_provider_user`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "服务商员工表", subTypes = {ProviderUser.class})
public class ProviderUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属服务商ID")
    @TableField("`PROVIDER_ID`")
    private Long providerId;

    @ApiModelProperty(value = "服务商关联会员ID")
    @TableField("`MEMBER_ID`")
    private Long memberId;

    @ApiModelProperty(value = "角色代码")
    @TableField("`ROLES_CODE`")
    private String rolesCode;

    @ApiModelProperty(value = "加入时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;


    public static final String ID = "`ID`";

    public static final String PROVIDER_ID = "`PROVIDER_ID`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String ROLES_CODE = "`ROLES_CODE`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

}
