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
 * 推送消息表
 * </p>
 *
 * @author xixil
 * @since 2018-10-23
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`im_push_message`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "推送消息表", subTypes = {PushMessage.class})
public class PushMessage implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "消息体")
    @TableField("`MESSAGE`")
    private String message;

    @ApiModelProperty(value = "接受者（ADMIN,CUSTOMER(PC,MP),GUEST,PRIVODER）")
    @TableField("`RECEVIER_ID`")
    private Long recevierId;

    @ApiModelProperty(value = "接收者类型(MEMBER,ADMIN,GUEST)")
    @TableField("`RECEVIER_Type`")
    private Integer recevierType;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "读取时间")
    @TableField("`READ_TIME`")
    private Date readTime;

    @ApiModelProperty(value = "状态")
    @TableField("`STATUS`")
    private Integer status;


    public static final String ID = "`ID`";

    public static final String MESSAGE = "`MESSAGE`";

    public static final String RECEVIER_ID = "`RECEVIER_ID`";

    public static final String RECEVIER_TYPE = "`RECEVIER_Type`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String READ_TIME = "`READ_TIME`";

    public static final String STATUS = "`STATUS`";

}
