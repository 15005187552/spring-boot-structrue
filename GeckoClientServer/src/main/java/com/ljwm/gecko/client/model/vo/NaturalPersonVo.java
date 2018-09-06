package com.ljwm.gecko.client.model.vo;

import com.ljwm.gecko.base.entity.NaturalPerson;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Janiffy
 * @date 2018/9/5 13:16
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NaturalPersonVo extends NaturalPerson {

  @ApiModelProperty(value = "会员ID")
  private Long memberId;

  @ApiModelProperty(value = "国籍 0-中国大陆 1-港澳台 2-外籍")
  private Integer country;

  @ApiModelProperty(value = "姓名")
  private String name;

  @ApiModelProperty(value = "证件类型")
  private Integer certificate;

  @ApiModelProperty(value = "证件号")
  private Long certNum;

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

  @ApiModelProperty(value = "社保属性 0-公司代缴 1-个人缴纳")
  private Integer socialSecu;

  @ApiModelProperty(value = "创建时间")
  private Date creatTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;
}
