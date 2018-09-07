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
 * 服务商证件类型表
 * </p>
 *
 * @author Levis
 * @since 2018-09-06
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_paper`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "服务商证件类型表", subTypes = {Paper.class})
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "证书名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "适用类型 0: 个人 1: 企业")
    @TableField("`TYPE`")
    private Integer type;
    @TableField("`DIABLED`")
    private Boolean diabled;
    @TableField("`CREATE_TIME`")
    private Date createTime;
    @TableField("`UPDATE_TIME`")
    private Date updateTime;


    public static final String ID = "`ID`";

    public static final String NAME = "`NAME`";

    public static final String TYPE = "`TYPE`";

    public static final String DIABLED = "`DIABLED`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

}
