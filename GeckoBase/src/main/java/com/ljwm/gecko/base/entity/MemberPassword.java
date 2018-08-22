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
 * 账号密码表
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-22
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("t_member_password")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "账号密码表", subTypes = {MemberPassword.class})
public class MemberPassword implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "密码ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "密码值")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "加密盐值")
    @TableField("SALT")
    private String salt;
    @TableField("LAST_MODIFY_TIME")
    private Date lastModifyTime;


    public static final String ID = "ID";

    public static final String PASSWORD = "PASSWORD";

    public static final String SALT = "SALT";

    public static final String LAST_MODIFY_TIME = "LAST_MODIFY_TIME";

}
