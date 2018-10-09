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
 * @author Levis
 * @since 2018-10-09
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_spec`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {Spec.class})
public class Spec implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "服务类型")
    @TableField("`SERVICE_ID`")
    private Integer serviceId;

    @ApiModelProperty(value = "规格名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private Integer sort;

    @ApiModelProperty(value = "父节点id")
    @TableField("`PID`")
    private Integer pid;

    @ApiModelProperty(value = "级别")
    @TableField("`LEVEL`")
    private String level;


    public static final String ID = "`ID`";

    public static final String SERVICE_ID = "`SERVICE_ID`";

    public static final String NAME = "`NAME`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String SORT = "`SORT`";

    public static final String PID = "`PID`";

    public static final String LEVEL = "`LEVEL`";

}
