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
 * 服务商证书表
 * </p>
 *
 * @author xixil
 * @since 2018-09-12
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_member_paper`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "服务商证书表", subTypes = {MemberPaper.class})
public class MemberPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "证件类型ID")
    @TableField("`PAPER_ID`")
    private Integer paperId;

    @ApiModelProperty(value = "所属服务商")
    @TableField("`MEMBER_ID`")
    private Long memberId;

    @ApiModelProperty(value = "证件文件路径")
    @TableField("`PIC_PATH`")
    private String picPath;
    @TableField("`CREATE_TIME`")
    private Date createTime;
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "审核状态  1 待审核 2 审核通过 3 审核失败")
    @TableField("`VALIDATE_STATE`")
    private Boolean validateState;

    @ApiModelProperty(value = "认证人id")
    @TableField("`VALIDATOR_ID`")
    private Long validatorId;

    @ApiModelProperty(value = "认证时间")
    @TableField("`VALIDATE_TIME`")
    private Date validateTime;

    @ApiModelProperty(value = "认证内容")
    @TableField("`VALIDATE_TEXT`")
    private String validateText;


    public static final String ID = "`ID`";

    public static final String PAPER_ID = "`PAPER_ID`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String PIC_PATH = "`PIC_PATH`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String VALIDATE_STATE = "`VALIDATE_STATE`";

    public static final String VALIDATOR_ID = "`VALIDATOR_ID`";

    public static final String VALIDATE_TIME = "`VALIDATE_TIME`";

    public static final String VALIDATE_TEXT = "`VALIDATE_TEXT`";

}
