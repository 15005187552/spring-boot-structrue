package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 权限表
 * </p>
 *
 * @author xixil
 * @since 2018-08-29
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_function`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "权限表", subTypes = {Function.class})
public class Function implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "菜单ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父节点ID")
    @TableField("`PARENT_ID`")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "标头")
    @TableField("`TITLE`")
    private String title;

    @ApiModelProperty(value = "描述")
    @TableField("`DESCRIPTION`")
    private String description;

    @ApiModelProperty(value = "图标路径")
    @TableField("`ICON`")
    private String icon;

    @ApiModelProperty(value = "链接页面")
    @TableField("`URL`")
    private String url;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private Integer sort;

    @ApiModelProperty(value = "是否展示")
    @TableField("`IS_SHOW`")
    private Integer isShow;

    @TableField("`DISABLED`")
    private Integer disabled;


    public static final String ID = "`ID`";

    public static final String PARENT_ID = "`PARENT_ID`";

    public static final String NAME = "`NAME`";

    public static final String TITLE = "`TITLE`";

    public static final String DESCRIPTION = "`DESCRIPTION`";

    public static final String ICON = "`ICON`";

    public static final String URL = "`URL`";

    public static final String SORT = "`SORT`";

    public static final String IS_SHOW = "`IS_SHOW`";

    public static final String DISABLED = "`DISABLED`";

}
