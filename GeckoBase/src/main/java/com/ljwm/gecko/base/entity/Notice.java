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
 * 公告表
 * </p>
 *
 * @author xixil
 * @since 2018-10-10
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_notice`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "公告表", subTypes = {Notice.class})
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "公告标题")
    @TableField("`TITLE`")
    private String title;

    @ApiModelProperty(value = "公告内容")
    @TableField("`CONTENT`")
    private String content;

    @ApiModelProperty(value = "标签id")
    @TableField("`TAG_ID`")
    private Integer tagId;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "启用禁用")
    @TableField("`DISABLED`")
    private Boolean disabled;


    public static final String ID = "`ID`";

    public static final String TITLE = "`TITLE`";

    public static final String CONTENT = "`CONTENT`";

    public static final String TAG_ID = "`TAG_ID`";

    public static final String SORT = "`SORT`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String DISABLED = "`DISABLED`";

}
