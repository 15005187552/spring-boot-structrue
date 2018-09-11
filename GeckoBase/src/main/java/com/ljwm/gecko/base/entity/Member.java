package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author Levis
 * @since 2018-09-10
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_member`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "会员表", subTypes = {Member.class})
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "会员自增ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "注册手机号")
    @TableField("`REG_MOBILE`")
    private String regMobile;

    @ApiModelProperty(value = "会员昵称")
    @TableField("`NICK_NAME`")
    private String nickName;

    @ApiModelProperty(value = "头像路径")
    @TableField("`AVATAR_PATH`")
    private String avatarPath;

    @ApiModelProperty(value = "会员身份证号")
    @TableField("`MEMBER_IDCARD`")
    private String memberIdcard;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "是否禁用")
    @TableField("`DISABLED`")
    private Boolean disabled;


    @TableField("`VALIDATER_ID`")
    private Long validaterId;

    @ApiModelProperty(value = "认证时间")
    @TableField("`VALIDATE_TIME`")
    private Date validateTime;

    @ApiModelProperty(value = "审核状态 0  初始状态   1 待审核 2 审核通过 3 审核失败")
    @TableField("`VALIDATE_STATE`")
    private Integer validateState;
    @TableField("`VALIDATE_TEXT`")
    private String validateText;


    public static final String ID = "`ID`";

    public static final String REG_MOBILE = "`REG_MOBILE`";

    public static final String NICK_NAME = "`NICK_NAME`";

    public static final String AVATAR_PATH = "`AVATAR_PATH`";

    public static final String MEMBER_IDCARD = "`MEMBER_IDCARD`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String DISABLED = "`DISABLED`";

    public static final String VALIDATER_ID = "`VALIDATER_ID`";

    public static final String VALIDATE_TIME = "`VALIDATE_TIME`";

    public static final String VALIDATE_STATE = "`VALIDATE_STATE`";

    public static final String VALIDATE_TEXT = "`VALIDATE_TEXT`";

}
