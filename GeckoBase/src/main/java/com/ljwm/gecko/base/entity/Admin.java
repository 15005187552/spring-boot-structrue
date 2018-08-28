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
 * 后台管理用户表
 * </p>
 *
 * @author xixil
 * @since 2018-08-27
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_admin`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "后台管理用户表", subTypes = {Admin.class})
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "用户名")
    @TableField("`USERNAME`")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("`PASSWORD`")
    private String password;

    @ApiModelProperty(value = "别称")
    @TableField("`NICK_NAME`")
    private String nickName;
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "启用禁用标示")
    @TableField("`DISABLED`")
    private Integer disabled;


    public static final String ID = "`ID`";

    public static final String USERNAME = "`USERNAME`";

    public static final String PASSWORD = "`PASSWORD`";

    public static final String NICK_NAME = "`NICK_NAME`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String DISABLED = "`DISABLED`";

}
