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
 * 
 * </p>
 *
 * @author xixil
 * @since 2018-10-11
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`im_push_message_type`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {PushMessageType.class})
public class PushMessageType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;
    @TableField("`PUSH_MESSAGE_ID`")
    private Long pushMessageId;

    @ApiModelProperty(value = "发送至那个端")
    @TableField("`TYPE`")
    private Integer type;

    @ApiModelProperty(value = "推送时间")
    @TableField("`PUSH_TIME`")
    private Date pushTime;

    @ApiModelProperty(value = "读取时间")
    @TableField("`RAED_TIME`")
    private Date raedTime;

    @ApiModelProperty(value = "状态")
    @TableField("`STATUS`")
    private Integer status;


    public static final String ID = "`ID`";

    public static final String PUSH_MESSAGE_ID = "`PUSH_MESSAGE_ID`";

    public static final String TYPE = "`TYPE`";

    public static final String PUSH_TIME = "`PUSH_TIME`";

    public static final String RAED_TIME = "`RAED_TIME`";

    public static final String STATUS = "`STATUS`";

}
