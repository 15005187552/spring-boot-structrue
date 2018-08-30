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
 * 广告数据表
 * </p>
 *
 * @author xixil
 * @since 2018-08-30
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_advertisement`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "广告数据表", subTypes = {Advertisement.class})
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "广告ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "图片路径")
    @TableField("`PATH`")
    private String path;

    @ApiModelProperty(value = "跳转路径")
    @TableField("`URL_PATH`")
    private String urlPath;

    @ApiModelProperty(value = "设备类型 对应枚举类型")
    @TableField("`EQUIP_TYPE`")
    private Integer equipType;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private Integer sort;

    @ApiModelProperty(value = "是否禁用 0-没有禁用 1-禁用")
    @TableField("`DISABLED`")
    private Integer disabled;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("`CREATER_ID`")
    private Long createrId;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "更新人ID")
    @TableField("`UPDATER_ID`")
    private Long updaterId;


    public static final String ID = "`ID`";

    public static final String PATH = "`PATH`";

    public static final String URL_PATH = "`URL_PATH`";

    public static final String EQUIP_TYPE = "`EQUIP_TYPE`";

    public static final String SORT = "`SORT`";

    public static final String DISABLED = "`DISABLED`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String CREATER_ID = "`CREATER_ID`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String UPDATER_ID = "`UPDATER_ID`";

}
