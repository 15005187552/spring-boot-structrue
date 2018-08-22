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
 * 会员账号表
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-22
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("t_member_account")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "会员账号表", subTypes = {MemberAccount.class})
public class MemberAccount implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "账号ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "账号ID")
    @TableField("USERNAME")
    private String username;

    @ApiModelProperty(value = "账号类型")
    @TableField("TYPE")
    private Integer type;

    @ApiModelProperty(value = "所属会员ID")
    @TableField("MEMBER_ID")
    private Long memberId;

    @ApiModelProperty(value = "关联密码ID")
    @TableField("PASSWORD_ID")
    private Long passwordId;

    @ApiModelProperty(value = "账号扩展信息")
    @TableField("EXT_INFO")
    private String extInfo;


    public static final String ID = "ID";

    public static final String USERNAME = "USERNAME";

    public static final String TYPE = "TYPE";

    public static final String MEMBER_ID = "MEMBER_ID";

    public static final String PASSWORD_ID = "PASSWORD_ID";

    public static final String EXT_INFO = "EXT_INFO";

}
