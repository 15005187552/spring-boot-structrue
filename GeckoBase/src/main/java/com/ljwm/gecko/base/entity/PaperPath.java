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
 * 服务商证书表
 * </p>
 *
 * @author xixil
 * @since 2018-09-12
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_paper_path`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "服务商证书表", subTypes = {PaperPath.class})
public class PaperPath implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "证件类型ID")
    @TableField("`MEMBER_PAPER_ID`")
    private Integer memberPaperId;

    @ApiModelProperty(value = "证件文件路径")
    @TableField("`PIC_PATH`")
    private String picPath;
    @TableField("`CREATE_TIME`")
    private Date createTime;
    @TableField("`UPDATE_TIME`")
    private Date updateTime;


    public static final String ID = "`ID`";

    public static final String MEMBER_PAPER_ID = "`MEMBER_PAPER_ID`";

    public static final String PIC_PATH = "`PIC_PATH`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

}
