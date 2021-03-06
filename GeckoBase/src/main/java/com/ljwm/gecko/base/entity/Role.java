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
 * @since 2018-09-10
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_role`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色表", subTypes = {Role.class})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色名称")
    @TableField("`ROLE_NAME`")
    private String roleName;

    @ApiModelProperty(value = "角色代码")
    @TableField("`ROLE_CODE`")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    @TableField("`ROLE_DESC`")
    private String roleDesc;

    @ApiModelProperty(value = "是否能够删除")
    @TableField("`DISABLED`")
    private Integer disabled;


    public static final String ID = "`ID`";

    public static final String ROLE_NAME = "`ROLE_NAME`";

    public static final String ROLE_CODE = "`ROLE_CODE`";

    public static final String ROLE_DESC = "`ROLE_DESC`";

    public static final String DISABLED = "`DISABLED`";

}
