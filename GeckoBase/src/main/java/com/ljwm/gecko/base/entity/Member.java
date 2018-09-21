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
 * @author xixil
 * @since 2018-09-12
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

    @ApiModelProperty("会员姓名")
    @TableField("`NAME`")
    private String name;

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

    @ApiModelProperty(value = "审核状态 0  初始状态   1 待审核 2 审核通过 3 审核失败")
    @TableField("`VALIDATE_STATE`")
    private Integer validateState;

    @ApiModelProperty(value = "身份证正面")
    @TableField("`PIC_FRONT`")
    private String picFront;

    @ApiModelProperty(value = "身份证反面")
    @TableField("`PIC_BACK`")
    private String picBack;

    @ApiModelProperty(value = "个人证件照")
    @TableField("`PIC_PASSPORT`")
    private String picPassport;

    @ApiModelProperty(value = "版本号")
    @TableField("`VERSION`")
    private Integer version;

    @ApiModelProperty(value = "会员基本信息认证状态  0  未认证  1 已认证")
    @TableField("INFO_VALIDATE_STATE")
    private Integer infoValidateState;

    @ApiModelProperty(value = "会员info认证信息")
    @TableField("VALIDATE_TEXT")
    private String validateText;

    @ApiModelProperty(value = "认证人")
    @TableField("VALIDATOR_ID")
    private Long validatorId;


    public static final String ID = "`ID`";

    public static final String REG_MOBILE = "`REG_MOBILE`";

    public static final String NICK_NAME = "`NICK_NAME`";

    public static final String AVATAR_PATH = "`AVATAR_PATH`";

    public static final String MEMBER_IDCARD = "`MEMBER_IDCARD`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String DISABLED = "`DISABLED`";

    public static final String VALIDATE_STATE = "`VALIDATE_STATE`";

    public static final String PIC_FRONT = "`PIC_FRONT`";

    public static final String PIC_BACK = "`PIC_BACK`";

    public static final String PIC_PASSPORT = "`PIC_PASSPORT`";

    public static final String NAME = "`NAME`";


}
