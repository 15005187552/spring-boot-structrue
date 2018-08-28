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
 * 会员表
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-22
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("t_member")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "会员表", subTypes = {Member.class})
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "会员自增ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "注册手机号")
    @TableField("REG_MOBILE")
    private String regMobile;

    @ApiModelProperty(value = "会员昵称")
    @TableField("NICK_NAME")
    private String nickName;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "是否禁用")
    @TableField("DISABLED")
    private Boolean disabled;


    public static final String ID = "ID";

    public static final String REG_MOBILE = "REG_MOBILE";

    public static final String NICK_NAME = "NICK_NAME";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String DISABLED = "DISABLED";

}