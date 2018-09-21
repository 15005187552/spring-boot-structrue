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
 * @since 2018-09-21
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
    @TableField("`BONUS`")
    private BigDecimal bonus;

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

    @ApiModelProperty(value = "薪资")
    @TableField("`SALARY`")
    private BigDecimal salary;

    @ApiModelProperty(value = "薪资调整")
    @TableField("`SALARY_ADJUST`")
    private BigDecimal salaryAdjust;

    @ApiModelProperty(value = "薪资说明")
    @TableField("`SALARY_REMARK`")
    private String salaryRemark;

    @ApiModelProperty(value = "全年一次性奖金补贴")
    @TableField("`BONUS_YEAR`")
    private BigDecimal bonusYear;

    @ApiModelProperty(value = "解除劳动关系一次性经济补偿金")
    @TableField("`COMPENSATION`")
    private BigDecimal compensation;

    @ApiModelProperty(value = "病事假说明")
    @TableField("`SICK_REMARK`")
    private String sickRemark;

    @ApiModelProperty(value = "处罚说明")
    @TableField("`PUNISH_REMARK`")
    private String punishRemark;

    @ApiModelProperty(value = "社保缴费基数")
    @TableField("`SOCIAL_BASE`")
    private BigDecimal socialBase;

    @ApiModelProperty(value = "社保公司比例")
    @TableField("`SOCIAL_COM_PER`")
    private BigDecimal socialComPer;

    @ApiModelProperty(value = "社保个人比例")
    @TableField("`SOCIAL_PER_PER`")
    private BigDecimal socialPerPer;

    @ApiModelProperty(value = "社保公司调整")
    @TableField("`SOCIAL_COM_AJUST`")
    private BigDecimal socialComAjust;

    @ApiModelProperty(value = "社保个人调整")
    @TableField("`SOCIAL_PER_AJUST`")
    private BigDecimal socialPerAjust;

    @ApiModelProperty(value = "社保说明")
    @TableField("`SOCIAL_REMARK`")
    private String socialRemark;

    @ApiModelProperty(value = "公积金基数")
    @TableField("`FUND_BASE`")
    private BigDecimal fundBase;

    @ApiModelProperty(value = "公积金公司缴费比例")
    @TableField("`FUND_COM_PER`")
    private BigDecimal fundComPer;

    @ApiModelProperty(value = "公积金个人缴费比例")
    @TableField("`FUND_PER_PER`")
    private BigDecimal fundPerPer;

    @ApiModelProperty(value = "公积金公司调整")
    @TableField("`FUND_COM_AJUST`")
    private BigDecimal fundComAjust;

    @ApiModelProperty(value = "公积金个人调整")
    @TableField("`FUND_PER_AJUST`")
    private BigDecimal fundPerAjust;

    @ApiModelProperty(value = "调整说明")
    @TableField("`FUND_REMARK`")
    private String fundRemark;


    public static final String ID = "`ID`";

    public static final String DATE = "`DATE`";

    public static final String COMPANY__USER_ID = "`COMPANY__USER_ID`";

    public static final String OVERTIME = "`OVERTIME`";

    public static final String BONUS = "`BONUS`";

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

    public static final String SALARY = "`SALARY`";

    public static final String SALARY_ADJUST = "`SALARY_ADJUST`";

    public static final String SALARY_REMARK = "`SALARY_REMARK`";

    public static final String BONUS_YEAR = "`BONUS_YEAR`";

    public static final String COMPENSATION = "`COMPENSATION`";

    public static final String SICK_REMARK = "`SICK_REMARK`";

    public static final String PUNISH_REMARK = "`PUNISH_REMARK`";

    public static final String SOCIAL_BASE = "`SOCIAL_BASE`";

    public static final String SOCIAL_COM_PER = "`SOCIAL_COM_PER`";

    public static final String SOCIAL_PER_PER = "`SOCIAL_PER_PER`";

    public static final String SOCIAL_COM_AJUST = "`SOCIAL_COM_AJUST`";

    public static final String SOCIAL_PER_AJUST = "`SOCIAL_PER_AJUST`";

    public static final String SOCIAL_REMARK = "`SOCIAL_REMARK`";

    public static final String FUND_BASE = "`FUND_BASE`";

    public static final String FUND_COM_PER = "`FUND_COM_PER`";

    public static final String FUND_PER_PER = "`FUND_PER_PER`";

    public static final String FUND_COM_AJUST = "`FUND_COM_AJUST`";

    public static final String FUND_PER_AJUST = "`FUND_PER_AJUST`";

    public static final String FUND_REMARK = "`FUND_REMARK`";

}
