package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

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

  @ApiModelProperty(value = "国籍 0-中国大陆 1-港澳台 2-外籍")
  private Integer country;

  @ApiModelProperty(value = "姓名")
  private String name;

  @ApiModelProperty(value = "证件类型 0-身份证号 1-港澳台证件号 3-外籍证件号")
  private Integer certificate;

  @ApiModelProperty(value = "证件号")
  private Integer certNum;

  @ApiModelProperty(value = "身份证正面图片")
  private MultipartFile posFile;

  @ApiModelProperty(value = "身份证反面图片")
  private MultipartFile oppoFile;

  @ApiModelProperty(value = "社保属性 0-公司代缴 1-个人缴纳")
  private Integer socialSecu;


}
