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
 * 报税数据主表
 * </p>
 *
 * @author Levis
 * @since 2018-09-05
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_tax`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "报税数据主表", subTypes = {Tax.class})
public class Tax implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "报税")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员ID")
    @TableField("`MEMBER_ID`")
    private Long memberId;

    @ApiModelProperty(value = "申报类型 0-月报 1-年报")
    @TableField("`DECLARE_TYPE`")
    private String declareType;

    @ApiModelProperty(value = "申报时段")
    @TableField("`DECLARE_TIME`")
    private String declareTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;


    public static final String ID = "`ID`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String DECLARE_TYPE = "`DECLARE_TYPE`";

    public static final String DECLARE_TIME = "`DECLARE_TIME`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

}
