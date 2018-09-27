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
 * @since 2018-09-27
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

    @ApiModelProperty(value = "时间")
    @TableField("`DATE`")
    private String date;

    @ApiModelProperty(value = "公司用户关联ID")
    @TableField("`COMPANY_USER_ID`")
    private Long companyUserId;

    @ApiModelProperty(value = "属性ID")
    @TableField("`ATTRIBUTE_ID`")
    private Long attributeId;

    @ApiModelProperty(value = "值")
    @TableField("`VALUE`")
    private String value;


    public static final String ID = "`ID`";

    public static final String DATE = "`DATE`";

    public static final String COMPANY_USER_ID = "`COMPANY_USER_ID`";

    public static final String ATTRIBUTE_ID = "`ATTRIBUTE_ID`";

    public static final String VALUE = "`VALUE`";

}
