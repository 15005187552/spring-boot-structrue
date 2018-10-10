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
 * 考勤报税数据主表
 * </p>
 *
 * @author Levis
 * @since 2018-10-10
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_tax`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "考勤报税数据主表", subTypes = {Tax.class})
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
    private Integer declareType;

    @ApiModelProperty(value = "申报时段")
    @TableField("`DECLARE_TIME`")
    private String declareTime;

    @ApiModelProperty(value = "数据状态")
    @TableField("`STATUS`")
    private Integer status;

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

    public static final String STATUS = "`STATUS`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

}
