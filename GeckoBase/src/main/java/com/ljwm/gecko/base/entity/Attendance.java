package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Levis
 * @since 2018-09-28
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_attendance`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {Attendance.class})
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "考勤报税数据ID")
    @TableField("`TAX_ID`")
    private String taxId;

    @ApiModelProperty(value = "属性ID")
    @TableField("`ATTRIBUTE_ID`")
    private Long attributeId;

    @ApiModelProperty(value = "值")
    @TableField("`VALUE`")
    private String value;


    public static final String ID = "`ID`";

    public static final String TAX_ID = "`TAX_ID`";

    public static final String ATTRIBUTE_ID = "`ATTRIBUTE_ID`";

    public static final String VALUE = "`VALUE`";

}
