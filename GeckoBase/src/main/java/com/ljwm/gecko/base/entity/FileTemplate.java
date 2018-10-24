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
 * @author kevin
 * @since 2018-10-24
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_file_template`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {FileTemplate.class})
public class FileTemplate implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "文件id")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    @TableField("`CREATOR_ID`")
    private Long creatorId;

    @ApiModelProperty(value = "文件下载所需费用")
    @TableField("`MONEY`")
    private Integer money;

    @ApiModelProperty(value = "文件名称")
    @TableField("`FILE_NAME`")
    private String fileName;

    @ApiModelProperty(value = "文件下载路径")
    @TableField("`FILE_PATH`")
    private String filePath;

    @ApiModelProperty(value = "文件首次下载时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "文件再次下载时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "0:启用  1：禁用")
    @TableField("`DISABLE`")
    private Integer disable;


    public static final String ID = "`ID`";

    public static final String CREATOR_ID = "`CREATOR_ID`";

    public static final String MONEY = "`MONEY`";

    public static final String FILE_NAME = "`FILE_NAME`";

    public static final String FILE_PATH = "`FILE_PATH`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String DISABLE = "`DISABLE`";

}
