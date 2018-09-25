package com.ljwm.gecko.client.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Janiffy
 * @date 2018/9/10 17:08
 */
@Data
public class PersonInfoDto implements Serializable {

  @ApiModelProperty(value = "工号")
  private String jobNum;

  @ApiModelProperty(value = "姓名")
  private String name;

  @ApiModelProperty(value = "证件类型")
  private String certificate;

  @ApiModelProperty(value = "证件号,此处参数为memberIdcard")
  @JSONField(name = "memberIdcard")
  private String certNum;

  @ApiModelProperty(value = "国籍 0-中国大陆 1-港澳台 2-外籍")
  private String country;

  @ApiModelProperty(value = "性别 0-男  1-女")
  private String gender;

  @ApiModelProperty(value = "出生年月")
  private String birthday;

  @ApiModelProperty(value = "学历")
  private String education;

  @ApiModelProperty(value = "人员状态")
  private String personState;

  @ApiModelProperty(value = "是否雇员")
  private String employee;

  @ApiModelProperty(value = "注册手机号")
  private String regMobile;

  @ApiModelProperty(value = "任职日期")
  private String hireDate;

  @ApiModelProperty(value = "员工类别")
  private String employeeType;

  @ApiModelProperty(value = "部门")
  private String department;

  @ApiModelProperty(value = "岗位")
  private String station;

  @ApiModelProperty(value = "离职日期")
  private String termDate;

  @ApiModelProperty(value = "社保缴费基数")
  private String socialBase;

  /*@ApiModelProperty(value = "社保公司比例")
  private String socialComPer;

  @ApiModelProperty(value = "公司养老保险比例")
  private String comPension;

  @ApiModelProperty(value = "公司医疗保险比例")
  private String comMedical;

  @ApiModelProperty(value = "公司失业保险比例")
  private String comUnemploy;

  @ApiModelProperty(value = "公司工伤保险比例")
  private String comInjury;

  @ApiModelProperty(value = "公司生育保险比例")
  private String comBirth;

  @ApiModelProperty(value = "社保个人比例")
  private String socialPersonPer;

  @ApiModelProperty(value = "个人养老保险比例")
  private String personPension;

  @ApiModelProperty(value = "个人医疗保险比例")
  private String personMedical;

  @ApiModelProperty(value = "个人失业保险比例")
  private String personUnemploy;*/

  @ApiModelProperty(value = "公积金基数")
  private String fundBase;

  @ApiModelProperty(value = "公司公积金比例")
  private String fundCom;

  @ApiModelProperty(value = "个人公积金比例")
  private String fundPerson;

  @ApiModelProperty(value = "工作城市")
  private String workCity;

  @ApiModelProperty(value = "婚姻状况")
  private String maritalStatus;

  @ApiModelProperty(value = "是否引进人才")
  private String ntroduceTalents;

  @ApiModelProperty(value = "开户银行")
  private String bank;

  @ApiModelProperty(value = "银行账号")
  private String bankNum;

  @ApiModelProperty(value = "社保账号")
  private String socialNum;

  @ApiModelProperty(value = "公积金账号")
  private String fundNum;

  @ApiModelProperty(value = "是否特定行业")
  private String specialIndustry;

  @ApiModelProperty(value = "是否股东投资人")
  private String isInvestor;

  @ApiModelProperty(value = "是否残疾")
  private String isDisability;

  @ApiModelProperty(value = "是否烈属")
  private String isMatrtyr;

  @ApiModelProperty(value = "是否孤老")
  private String isOld;

  @ApiModelProperty(value = "残疾证号")
  private String disablityNum;

  @ApiModelProperty(value = "烈属证号")
  private String matrtyrNum;

  @ApiModelProperty(value = "电子邮箱")
  private String email;

  @ApiModelProperty(value = "省")
  private String province;

  @ApiModelProperty(value = "城市")
  private String city;

  @ApiModelProperty(value = "区")
  private String area;

  @ApiModelProperty(value = "详细地址")
  private String address;

  @ApiModelProperty(value = "备注")
  private String remark;
}
