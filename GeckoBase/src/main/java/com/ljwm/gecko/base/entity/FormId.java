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
 * 
 * </p>
 *
 * @author Levis
 * @since 2018-09-20
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_form_id`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {FormId.class})
public class FormId implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "formId主键 自增")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "formId")
    @TableField("`FORM_ID`")
    private String formId;

    @ApiModelProperty(value = "会员ID")
    @TableField("`MEMBER_ID`")
    private Long memberId;

    @ApiModelProperty(value = "状态")
    @TableField("`STATUS`")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;


    public static final String ID = "`ID`";

    public static final String FORM_ID = "`FORM_ID`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String STATUS = "`STATUS`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

}
