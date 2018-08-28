package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 角色表
 * </p>
 *
 * @author xixil
 * @since 2018-08-28
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_role`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色表", subTypes = {Role.class})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.INPUT)
    private String id;
    @TableField("`ROLE_NAME`")
    private String roleName;

    @ApiModelProperty(value = "启用禁用表示 0启用")
    @TableField("`DISABLED`")
    private Integer disabled;


    public static final String ID = "`ID`";

    public static final String ROLE_NAME = "`ROLE_NAME`";

    public static final String DISABLED = "`DISABLED`";

}
