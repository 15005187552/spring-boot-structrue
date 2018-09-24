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
 * 客服消息表
 * </p>
 *
 * @author YunQiSong
 * @since 2018-09-24
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`im_customer_message`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "客服消息表", subTypes = {CustomerMessage.class})
public class CustomerMessage implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "属于的会话")
    @TableField("`CUSTOMER_SESSION_ID`")
    private Long customerSessionId;

    @ApiModelProperty(value = "发送者ID，发送者可以为游客，客户，客服")
    @TableField("`SENDER_ID`")
    private Long senderId;

    @ApiModelProperty(value = "发送者不同类型")
    @TableField("`SENDER_TYPE`")
    private Integer senderType;

    @ApiModelProperty(value = "客户消息状态（未发，未读，已读）")
    @TableField("`CUSTOMER_STATUS`")
    private Integer customerStatus;

    @ApiModelProperty(value = "服务商（接待处理）状态（未发，未读，已读）")
    @TableField("`PROVIDER_STATUS`")
    private Integer providerStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "推送时间")
    @TableField("`PUSH_TIME`")
    private Date pushTime;

    @ApiModelProperty(value = "处理时间")
    @TableField("`HAND_TIME`")
    private Date handTime;


    public static final String ID = "`ID`";

    public static final String CUSTOMER_SESSION_ID = "`CUSTOMER_SESSION_ID`";

    public static final String SENDER_ID = "`SENDER_ID`";

    public static final String SENDER_TYPE = "`SENDER_TYPE`";

    public static final String CUSTOMER_STATUS = "`CUSTOMER_STATUS`";

    public static final String PROVIDER_STATUS = "`PROVIDER_STATUS`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String PUSH_TIME = "`PUSH_TIME`";

    public static final String HAND_TIME = "`HAND_TIME`";

}
