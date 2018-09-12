package com.ljwm.gecko.base.entity;

import java.math.BigDecimal;
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
 * 
 * </p>
 *
 * @author Levis
 * @since 2018-09-12
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
    @TableField("`COMPANY__USER_ID`")
    private Long companyUserId;

    @ApiModelProperty(value = "加班")
    @TableField("`OVERTIME`")
    private BigDecimal overtime;

    @ApiModelProperty(value = "定期奖金")
    @TableField("`BONUSES`")
    private BigDecimal bonuses;

    @ApiModelProperty(value = "考勤绩效")
    @TableField("`APPRAISAL`")
    private BigDecimal appraisal;

    @ApiModelProperty(value = "其他工资收入")
    @TableField("`OTHER_INCOME`")
    private BigDecimal otherIncome;

    @ApiModelProperty(value = "事假")
    @TableField("`AFFAIR_DAY`")
    private BigDecimal affairDay;

    @ApiModelProperty(value = "病假")
    @TableField("`SICK_LEAVE`")
    private BigDecimal sickLeave;

    @ApiModelProperty(value = "迟到早退")
    @TableField("`TARDINESS`")
    private BigDecimal tardiness;

    @ApiModelProperty(value = "其他缺勤")
    @TableField("`OTHER_ABSENCE`")
    private BigDecimal otherAbsence;

    @ApiModelProperty(value = "旷工")
    @TableField("`ABSENTEEISM`")
    private BigDecimal absenteeism;

    @ApiModelProperty(value = "离岗")
    @TableField("`DIMISSION`")
    private BigDecimal dimission;

    @ApiModelProperty(value = "罚款")
    @TableField("`FINE`")
    private BigDecimal fine;

    @ApiModelProperty(value = "其他处罚")
    @TableField("`OTHER_FINE`")
    private BigDecimal otherFine;


    public static final String ID = "`ID`";

    public static final String DATE = "`DATE`";

    public static final String COMPANY__USER_ID = "`COMPANY__USER_ID`";

    public static final String OVERTIME = "`OVERTIME`";

    public static final String BONUSES = "`BONUSES`";

    public static final String APPRAISAL = "`APPRAISAL`";

    public static final String OTHER_INCOME = "`OTHER_INCOME`";

    public static final String AFFAIR_DAY = "`AFFAIR_DAY`";

    public static final String SICK_LEAVE = "`SICK_LEAVE`";

    public static final String TARDINESS = "`TARDINESS`";

    public static final String OTHER_ABSENCE = "`OTHER_ABSENCE`";

    public static final String ABSENTEEISM = "`ABSENTEEISM`";

    public static final String DIMISSION = "`DIMISSION`";

    public static final String FINE = "`FINE`";

    public static final String OTHER_FINE = "`OTHER_FINE`";

}
