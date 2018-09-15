package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/3 10:17
 *
 * 个人信息实体类
 */
@Data
@Accessors(chain = true)
@ApiModel("个人信息表单")
public class PersonInfoForm {

  @ApiModelProperty(value = "会员ID")
  private Long memberId;

  @ApiModelProperty(value = "纳税公司ID")
  private Long companyId;

  @ApiModelProperty(value = "国籍 0-中国大陆 1-港澳台 2-外籍")
  private Integer country;

  @ApiModelProperty(value = "姓名")
  private String name;

  @ApiModelProperty(value = "证件类型")
  private Integer certificate;

  @ApiModelProperty(value = "证件号")
  private String certNum;

  @ApiModelProperty(value = "社保属性 0-公司代缴 1-个人缴纳")
  private Integer socialSecu;

  @ApiModelProperty(value = "残疾人证件号")
  private String disablityNum;

  @ApiModelProperty(value = "残疾证件路径")
  private String disablityPath;

  @ApiModelProperty(value = "烈属证件号")
  private String matrtyrNum;

  @ApiModelProperty(value = "烈属证件路径")
  private String matrtyrPath;

  @ApiModelProperty(value = "孤老证件号")
  private String oldNum;

  @ApiModelProperty(value = "孤老路径")
  private String oldPath;

  @ApiModelProperty(value = "专家学者证件号")
  private String professorNum;

  @ApiModelProperty(value = "专家学者证件路径")
  private String professorPath;

  @ApiModelProperty(value = "院士证件号")
  private String academicNum;

  @ApiModelProperty(value = "院士证件路径")
  private String academicPath;

}
