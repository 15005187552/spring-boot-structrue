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
import java.util.Date;

/**
 * <p>
 * 自然人纳税基本信息表
 * </p>
 *
 * @author Levis
 * @since 2018-09-03
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_natural_person`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "自然人纳税基本信息表", subTypes = {NaturalPerson.class})
public class NaturalPerson implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "会员ID")
    @TableId(value = "`MEMBER_ID`", type = IdType.INPUT)
    private Long memberId;

    @ApiModelProperty(value = "国籍 0-中国大陆 1-港澳台 2-外籍")
    @TableField("`COUNTRY`")
    private Integer country;

    @ApiModelProperty(value = "姓名")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "证件类型 0-身份证号 1-港澳台证件号 3-外籍证件号")
    @TableField("`CERTIFICATE`")
    private Integer certificate;

    @ApiModelProperty(value = "证件号")
    @TableField("`CERT_NUM`")
    private Long certNum;

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


    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String COUNTRY = "`COUNTRY`";

    public static final String NAME = "`NAME`";

    public static final String CERTIFICATE = "`CERTIFICATE`";

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
