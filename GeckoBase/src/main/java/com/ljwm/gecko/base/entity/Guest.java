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
 * 游客表
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-22
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("t_guest")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "游客表", subTypes = {Guest.class})
public class Guest implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "游客自增ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "游客的UUID, 用于标识游客")
    @TableField("GUEST_ID")
    private String guestId;

    @ApiModelProperty(value = "游客来源，枚举")
    @TableField("SOURCE_TYPE")
    private Integer sourceType;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "最后活动时间")
    @TableField("LAST_ACTIVE_TIME")
    private Date lastActiveTime;

    @ApiModelProperty(value = "扩展信息")
    @TableField("EXT_INFO")
    private String extInfo;

    @ApiModelProperty(value = "已经转化为会员是的会员ID")
    @TableField("MEMBER_ID")
    private Long memberId;


    public static final String ID = "ID";

    public static final String GUEST_ID = "GUEST_ID";

    public static final String SOURCE_TYPE = "SOURCE_TYPE";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String LAST_ACTIVE_TIME = "LAST_ACTIVE_TIME";

    public static final String EXT_INFO = "EXT_INFO";

    public static final String MEMBER_ID = "MEMBER_ID";

}
