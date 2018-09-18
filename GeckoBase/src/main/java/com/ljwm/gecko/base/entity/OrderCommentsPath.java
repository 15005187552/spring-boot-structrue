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
 * 订单评价图片表
 * </p>
 *
 * @author Livis
 * @since 2018-09-18
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_order_comments_path`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单评价图片表", subTypes = {OrderCommentsPath.class})
public class OrderCommentsPath implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "评价图片ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联的评价ID")
    @TableField("`COMMENT_ID`")
    private Long commentId;

    @ApiModelProperty(value = "评价创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "图片路径")
    @TableField("`PIC_PATH`")
    private String picPath;


    public static final String ID = "`ID`";

    public static final String COMMENT_ID = "`COMMENT_ID`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String PIC_PATH = "`PIC_PATH`";

}
