package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 
 * </p>
 *
 * @author Levis
 * @since 2018-09-26
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_attendance_template`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {AttendanceTemplate.class})
public class AttendanceTemplate implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "考勤模板表ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "公司ID")
    @TableField("`COMPANY_ID`")
    private Long companyId;

    @ApiModelProperty(value = "属性表ID")
    @TableField("`ATTRIBUTE_ID`")
    private Long attributeId;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private Integer sort;


    public static final String ID = "`ID`";

    public static final String COMPANY_ID = "`COMPANY_ID`";

    public static final String ATTRIBUTE_ID = "`ATTRIBUTE_ID`";

    public static final String SORT = "`SORT`";

}
