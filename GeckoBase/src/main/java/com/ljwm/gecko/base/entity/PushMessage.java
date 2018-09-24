package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 推送消息表
 * </p>
 *
 * @author YunQiSong
 * @since 2018-09-24
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

    @ApiModelProperty(value = "消息类型（不同类型映射接受者查询不同的表）")
    @TableField("`TYPE`")
    private Integer type;

    @ApiModelProperty(value = "状态（推送状态枚举）")
    @TableField("`STATUS`")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "推送时间")
    @TableField("`PUSH_TIME`")
    private Date pushTime;

    @ApiModelProperty(value = "阅读时间")
    @TableField("`READ_TIME`")
    private Date readTime;


    public static final String ID = "`ID`";

    public static final String MESSAGE = "`MESSAGE`";

    public static final String RECEVIER_ID = "`RECEVIER_ID`";

    public static final String TYPE = "`TYPE`";

    public static final String STATUS = "`STATUS`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String PUSH_TIME = "`PUSH_TIME`";

    public static final String READ_TIME = "`READ_TIME`";

}
