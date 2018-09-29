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
 * 客服会话表
 * </p>
 *
 * @author xixil
 * @since 2018-09-29
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`im_customer_session`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "客服会话表", subTypes = {CustomerSession.class})
public class CustomerSession implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会话名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "客户的会员ID")
    @TableField("`CUSTOMER_MEMBER_ID`")
    private Long customerMemberId;

    @ApiModelProperty(value = "客户的游客ID")
    @TableField("`CUSTOMER_GUEST_ID`")
    private Long customerGuestId;

    @ApiModelProperty(value = "当前接待人的会员ID")
    @TableField("`RECEPTIONIST_MEMBER_ID`")
    private Long receptionistMemberId;

    @ApiModelProperty(value = "当前服务商的ID")
    @TableField("`PROVIDER_ID`")
    private Long providerId;

    @ApiModelProperty(value = "会话状态（枚举）")
    @TableField("`STATUS`")
    private Integer status;


    public static final String ID = "`ID`";

    public static final String NAME = "`NAME`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String CUSTOMER_MEMBER_ID = "`CUSTOMER_MEMBER_ID`";

    public static final String CUSTOMER_GUEST_ID = "`CUSTOMER_GUEST_ID`";

    public static final String RECEPTIONIST_MEMBER_ID = "`RECEPTIONIST_MEMBER_ID`";

    public static final String PROVIDER_ID = "`PROVIDER_ID`";

    public static final String STATUS = "`STATUS`";

}
