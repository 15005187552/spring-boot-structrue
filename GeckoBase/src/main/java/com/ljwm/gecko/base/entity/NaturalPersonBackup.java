package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 自然人纳税基本信息表
 * </p>
 *
 * @author Levis
 * @since 2018-10-15
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_natural_person_backup`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "自然人纳税基本信息表", subTypes = {NaturalPersonBackup.class})
public class NaturalPersonBackup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("`ID`")
    private Long id;

    @ApiModelProperty(value = "会员ID")
    @TableField("`MEMBER_ID`")
    private Long memberId;
    @TableField("`COMPANY_ID`")
    private Long companyId;
    @TableField("`TAX_ID`")
    private Long taxId;

    @ApiModelProperty(value = "国籍 0-中国大陆 1-港澳台 2-外籍")
    @TableField("`COUNTRY`")
    private Integer country;

    @ApiModelProperty(value = "姓名")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "性别 0-男  1-女")
    @TableField("`GENDER`")
    private Integer gender;

    @ApiModelProperty(value = "出生年月")
    @TableField("`BIRTHDAY`")
    private Date birthday;

    @ApiModelProperty(value = "证件类型 0-身份证号 1-港澳台证件号 3-外籍证件号")
    @TableField("`CERTIFICATE`")
    private Integer certificate;

    @ApiModelProperty(value = "省")
    @TableField("`PROVINCE`")
    private Integer province;

    @ApiModelProperty(value = "城市")
    @TableField("`CITY`")
    private Integer city;

    @ApiModelProperty(value = "区")
    @TableField("`AREA`")
    private Integer area;

    @ApiModelProperty(value = "详细地址")
    @TableField("`ADDRESS`")
    private String address;

    @ApiModelProperty(value = "证件号")
    @TableField("`CERT_NUM`")
    private String certNum;

    @ApiModelProperty(value = "证件照正面")
    @TableField("`CERT_POS_PATH`")
    private String certPosPath;

    @ApiModelProperty(value = "证件照反面")
    @TableField("`CERT_OPPO_PATH`")
    private String certOppoPath;

    @ApiModelProperty(value = "残疾人证件号")
    @TableField("`DISABLITY_NUM`")
    private String disablityNum;

    @ApiModelProperty(value = "残疾证件路径")
    @TableField("`DISABLITY_PATH`")
    private String disablityPath;

    @ApiModelProperty(value = "烈属证件号")
    @TableField("`MATRTYR_NUM`")
    private String matrtyrNum;

    @ApiModelProperty(value = "烈属证件路径")
    @TableField("`MATRTYR_PATH`")
    private String matrtyrPath;

    @ApiModelProperty(value = "孤老证件号")
    @TableField("`OLD_NUM`")
    private String oldNum;

    @ApiModelProperty(value = "孤老路径")
    @TableField("`OLD_PATH`")
    private String oldPath;

    @ApiModelProperty(value = "专家学者证件号")
    @TableField("`PROFESSOR_NUM`")
    private String professorNum;

    @ApiModelProperty(value = "专家学者证件路径")
    @TableField("`PROFESSOR_PATH`")
    private String professorPath;

    @ApiModelProperty(value = "院士证件号")
    @TableField("`ACADEMIC_NUM`")
    private String academicNum;

    @ApiModelProperty(value = "院士证件路径")
    @TableField("`ACADEMIC_PATH`")
    private String academicPath;

    @ApiModelProperty(value = "社保属性 0-公司代缴 1-个人缴纳")
    @TableField("`SOCIAL_SECU`")
    private Integer socialSecu;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREAT_TIME`")
    private Date creatTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;


    public static final String ID = "`ID`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String COMPANY_ID = "`COMPANY_ID`";

    public static final String TAX_ID = "`TAX_ID`";

    public static final String COUNTRY = "`COUNTRY`";

    public static final String NAME = "`NAME`";

    public static final String GENDER = "`GENDER`";

    public static final String BIRTHDAY = "`BIRTHDAY`";

    public static final String CERTIFICATE = "`CERTIFICATE`";

    public static final String PROVINCE = "`PROVINCE`";

    public static final String CITY = "`CITY`";

    public static final String AREA = "`AREA`";

    public static final String ADDRESS = "`ADDRESS`";

    public static final String CERT_NUM = "`CERT_NUM`";

    public static final String CERT_POS_PATH = "`CERT_POS_PATH`";

    public static final String CERT_OPPO_PATH = "`CERT_OPPO_PATH`";

    public static final String DISABLITY_NUM = "`DISABLITY_NUM`";

    public static final String DISABLITY_PATH = "`DISABLITY_PATH`";

    public static final String MATRTYR_NUM = "`MATRTYR_NUM`";

    public static final String MATRTYR_PATH = "`MATRTYR_PATH`";

    public static final String OLD_NUM = "`OLD_NUM`";

    public static final String OLD_PATH = "`OLD_PATH`";

    public static final String PROFESSOR_NUM = "`PROFESSOR_NUM`";

    public static final String PROFESSOR_PATH = "`PROFESSOR_PATH`";

    public static final String ACADEMIC_NUM = "`ACADEMIC_NUM`";

    public static final String ACADEMIC_PATH = "`ACADEMIC_PATH`";

    public static final String SOCIAL_SECU = "`SOCIAL_SECU`";

    public static final String CREAT_TIME = "`CREAT_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

}
