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
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 公司员工信息详情表
 * </p>
 *
 * @author Levis
 * @since 2018-09-10
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_company_user_info`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "公司员工信息详情表", subTypes = {CompanyUserInfo.class})
public class CompanyUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID主键")
    @TableId(value = "`COMPANY_USER_ID`", type = IdType.INPUT)
    private Long companyUserId;

    @ApiModelProperty(value = "工号")
    @TableField("`JOB_NUM`")
    private String jobNum;

    @ApiModelProperty(value = "学历")
    @TableField("`EDUCATION`")
    private Integer education;

    @ApiModelProperty(value = "人员状态")
    @TableField("`PERSON_STATE`")
    private Integer personState;

    @ApiModelProperty(value = "是否雇员")
    @TableField("`EMPLOYEE`")
    private Integer employee;

    @ApiModelProperty(value = "任职日期")
    @TableField("`HIRE_DATE`")
    private Date hireDate;

    @ApiModelProperty(value = "员工类别")
    @TableField("`EMPLOYEE_TYPE`")
    private String employeeType;

    @ApiModelProperty(value = "部门")
    @TableField("`DEPARTMENT`")
    private String department;

    @ApiModelProperty(value = "岗位")
    @TableField("`STATION`")
    private String station;

    @ApiModelProperty(value = "离职日期")
    @TableField("`TERM_DATE`")
    private Date termDate;

    @ApiModelProperty(value = "社保缴费基数")
    @TableField("`SOCIAL_BASE`")
    private BigDecimal socialBase;

    @ApiModelProperty(value = "社保公司比例")
    @TableField("`SOCIAL_COM_PER`")
    private BigDecimal socialComPer;

    @ApiModelProperty(value = "公司养老保险比例")
    @TableField("`COM_PENSION`")
    private BigDecimal comPension;

    @ApiModelProperty(value = "公司医疗保险比例")
    @TableField("`COM_MEDICAL`")
    private BigDecimal comMedical;

    @ApiModelProperty(value = "公司失业保险比例")
    @TableField("`COM_UNEMPLOY`")
    private BigDecimal comUnemploy;

    @ApiModelProperty(value = "公司工伤保险比例")
    @TableField("`COM_INJURY`")
    private BigDecimal comInjury;

    @ApiModelProperty(value = "公司生育保险比例")
    @TableField("`COM_BIRTH`")
    private BigDecimal comBirth;

    @ApiModelProperty(value = "社保个人比例")
    @TableField("`SOCIAL_PERSON_PER`")
    private BigDecimal socialPersonPer;

    @ApiModelProperty(value = "个人养老保险比例")
    @TableField("`PERSON_PENSION`")
    private BigDecimal personPension;

    @ApiModelProperty(value = "个人医疗保险比例")
    @TableField("`PERSON_MEDICAL`")
    private BigDecimal personMedical;

    @ApiModelProperty(value = "个人失业保险比例")
    @TableField("`PERSON_UNEMPLOY`")
    private BigDecimal personUnemploy;

    @ApiModelProperty(value = "公积金基数")
    @TableField("`FUND_BASE`")
    private BigDecimal fundBase;

    @ApiModelProperty(value = "公司公积金比例")
    @TableField("`FUND_COM`")
    private BigDecimal fundCom;

    @ApiModelProperty(value = "个人公积金比例")
    @TableField("`FUND_PERSON`")
    private BigDecimal fundPerson;

    @ApiModelProperty(value = "城市编码")
    @TableField("`WORK_CITY`")
    private Integer workCity;

    @ApiModelProperty(value = "婚姻状况")
    @TableField("`MARITAL_STATUS`")
    private Integer maritalStatus;

    @ApiModelProperty(value = "是否引进人才")
    @TableField("`NTRODUCE_TALENTS`")
    private Integer ntroduceTalents;

    @ApiModelProperty(value = "开户银行")
    @TableField("`BANK`")
    private String bank;

    @ApiModelProperty(value = "银行账号")
    @TableField("`BANK_NUM`")
    private String bankNum;

    @ApiModelProperty(value = "社保账号")
    @TableField("`SOCIAL_NUM`")
    private String socialNum;

    @ApiModelProperty(value = "公积金账号")
    @TableField("`FUND_NUM`")
    private String fundNum;

    @ApiModelProperty(value = "是否特定行业")
    @TableField("`SPECIAL_INDUSTRY`")
    private Integer specialIndustry;

    @ApiModelProperty(value = "是否股东投资人")
    @TableField("`IS_INVESTOR`")
    private Integer isInvestor;

    @ApiModelProperty(value = "电子邮箱")
    @TableField("`EMAIL`")
    private String email;

    @ApiModelProperty(value = "备注")
    @TableField("`REMARK`")
    private String remark;


    public static final String COMPANY_USER_ID = "`COMPANY_USER_ID`";

    public static final String JOB_NUM = "`JOB_NUM`";

    public static final String EDUCATION = "`EDUCATION`";

    public static final String PERSON_STATE = "`PERSON_STATE`";

    public static final String EMPLOYEE = "`EMPLOYEE`";

    public static final String HIRE_DATE = "`HIRE_DATE`";

    public static final String EMPLOYEE_TYPE = "`EMPLOYEE_TYPE`";

    public static final String DEPARTMENT = "`DEPARTMENT`";

    public static final String STATION = "`STATION`";

    public static final String TERM_DATE = "`TERM_DATE`";

    public static final String SOCIAL_BASE = "`SOCIAL_BASE`";

    public static final String SOCIAL_COM_PER = "`SOCIAL_COM_PER`";

    public static final String COM_PENSION = "`COM_PENSION`";

    public static final String COM_MEDICAL = "`COM_MEDICAL`";

    public static final String COM_UNEMPLOY = "`COM_UNEMPLOY`";

    public static final String COM_INJURY = "`COM_INJURY`";

    public static final String COM_BIRTH = "`COM_BIRTH`";

    public static final String SOCIAL_PERSON_PER = "`SOCIAL_PERSON_PER`";

    public static final String PERSON_PENSION = "`PERSON_PENSION`";

    public static final String PERSON_MEDICAL = "`PERSON_MEDICAL`";

    public static final String PERSON_UNEMPLOY = "`PERSON_UNEMPLOY`";

    public static final String FUND_BASE = "`FUND_BASE`";

    public static final String FUND_COM = "`FUND_COM`";

    public static final String FUND_PERSON = "`FUND_PERSON`";

    public static final String WORK_CITY = "`WORK_CITY`";

    public static final String MARITAL_STATUS = "`MARITAL_STATUS`";

    public static final String NTRODUCE_TALENTS = "`NTRODUCE_TALENTS`";

    public static final String BANK = "`BANK`";

    public static final String BANK_NUM = "`BANK_NUM`";

    public static final String SOCIAL_NUM = "`SOCIAL_NUM`";

    public static final String FUND_NUM = "`FUND_NUM`";

    public static final String SPECIAL_INDUSTRY = "`SPECIAL_INDUSTRY`";

    public static final String IS_INVESTOR = "`IS_INVESTOR`";

    public static final String EMAIL = "`EMAIL`";

    public static final String REMARK = "`REMARK`";

}
