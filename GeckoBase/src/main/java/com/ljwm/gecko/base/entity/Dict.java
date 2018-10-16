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
 * 字典表
 * </p>
 *
 * @author Levis
 * @since 2018-10-16
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_dict`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "字典表", subTypes = {Dict.class})
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "键")
    @TableId(value = "`KEY`", type = IdType.AUTO)
    private String key;

    @ApiModelProperty(value = "值")
    @TableField("`VALUE`")
    private String value;

    @ApiModelProperty(value = "描述")
    @TableField("`DESC`")
    private String desc;


    public static final String KEY = "`KEY`";

    public static final String VALUE = "`VALUE`";

    public static final String DESC = "`DESC`";

}
