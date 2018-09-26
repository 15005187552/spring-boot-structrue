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
 * 服务商证书表
 * </p>
 *
 * @author Livis
 * @since 2018-09-26
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_provider_goods_path`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "服务商证书表", subTypes = {ProviderGoodsPath.class})
public class ProviderGoodsPath implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "证件类型ID")
    @TableField("`GOOD_ID`")
    private Long goodId;

    @ApiModelProperty(value = "证件文件路径")
    @TableField("`PIC_PATH`")
    private String picPath;
    @TableField("`CREATE_TIME`")
    private Date createTime;
    @TableField("`UPDATE_TIME`")
    private Date updateTime;


    public static final String ID = "`ID`";

    public static final String GOOD_ID = "`GOOD_ID`";

    public static final String PIC_PATH = "`PIC_PATH`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

}
