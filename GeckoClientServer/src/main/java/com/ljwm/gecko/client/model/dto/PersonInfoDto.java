package com.ljwm.gecko.client.model.dto;

import com.ljwm.gecko.base.utils.excelutil.ExcelCell;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Janiffy
 * @date 2018/9/10 17:08
 */
@Data
public class PersonInfoDto implements Serializable {

  @ExcelCell(index = 0)
  @ApiModelProperty(value = "工号")
  private String jobNum;

  @ExcelCell(index = 1)
  @ApiModelProperty(value = "姓名")
  private String name;

  @ExcelCell(index = 2)
  @ApiModelProperty(value = "证件类型")
  private String certificate;

  @ExcelCell(index = 3)
  @ApiModelProperty(value = "证件号")
  private String certNum;

  @ExcelCell(index = 4)
  @ApiModelProperty(value = "国籍 0-中国大陆 1-港澳台 2-外籍")
  private String country;

  @ExcelCell(index = 5)
  @ApiModelProperty(value = "性别 0-男  1-女")
  private String gender;

  @ExcelCell(index = 6)
  @ApiModelProperty(value = "出生年月")
  private String birthday;

  @ExcelCell(index = 7)
  @ApiModelProperty(value = "学历")
  private String education;

  @ExcelCell(index = 8)
  @ApiModelProperty(value = "人员状态")
  private String personState;

  @ExcelCell(index = 9)
  @ApiModelProperty(value = "是否雇员")
  private String employee;

  @ExcelCell(index = 10)
  @ApiModelProperty(value = "注册手机号")
  private String regMobile;

  @ExcelCell(index = 11)
  @ApiModelProperty(value = "任职日期")
  private String hireDate;

  @ExcelCell(index = 12)
  @ApiModelProperty(value = "员工类别")
  private String employeeType;

  @ExcelCell(index = 13)
  @ApiModelProperty(value = "部门")
  private String department;

  @ExcelCell(index = 14)
  @ApiModelProperty(value = "岗位")
  private String station;

  @ExcelCell(index = 15)
  @ApiModelProperty(value = "离职日期")
  private String termDate;

  @ExcelCell(index = 16)
  @ApiModelProperty(value = "社保缴费基数")
  private String socialBase;

  @ExcelCell(index = 17)
  @ApiModelProperty(value = "社保公司比例")
  private String socialComPer;

  @ExcelCell(index = 18)
  @ApiModelProperty(value = "公司养老保险比例")
  private String comPension;

  @ExcelCell(index = 19)
  @ApiModelProperty(value = "公司医疗保险比例")
  private String comMedical;

  @ExcelCell(index = 20)
  @ApiModelProperty(value = "公司失业保险比例")
  private String comUnemploy;

  @ExcelCell(index = 21)
  @ApiModelProperty(value = "公司工伤保险比例")
  private String comInjury;

  @ExcelCell(index = 22)
  @ApiModelProperty(value = "公司生育保险比例")
  private String comBirth;

  @ExcelCell(index = 23)
  @ApiModelProperty(value = "社保个人比例")
  private String socialPersonPer;

  @ExcelCell(index = 24)
  @ApiModelProperty(value = "个人养老保险比例")
  private String personPension;

  @ExcelCell(index = 24)
  @ApiModelProperty(value = "个人医疗保险比例")
  private String personMedical;

  @ExcelCell(index = 26)
  @ApiModelProperty(value = "个人失业保险比例")
  private String personUnemploy;

  @ExcelCell(index = 27)
  @ApiModelProperty(value = "公积金基数")
  private String fundBase;

  @ExcelCell(index = 28)
  @ApiModelProperty(value = "公司公积金比例")
  private String fundCom;

  @ExcelCell(index = 29)
  @ApiModelProperty(value = "个人公积金比例")
  private String fundPerson;

  @ExcelCell(index = 30)
  @ApiModelProperty(value = "工作城市")
  private String workCity;

  @ExcelCell(index = 31)
  @ApiModelProperty(value = "婚姻状况")
  private String maritalStatus;

  @ExcelCell(index = 32)
  @ApiModelProperty(value = "是否引进人才")
  private String ntroduceTalents;

  @ExcelCell(index = 33)
  @ApiModelProperty(value = "开户银行")
  private String bank;

  @ExcelCell(index = 34)
  @ApiModelProperty(value = "银行账号")
  private String bankNum;

  @ExcelCell(index = 35)
  @ApiModelProperty(value = "社保账号")
  private String socialNum;

  @ExcelCell(index = 36)
  @ApiModelProperty(value = "公积金账号")
  private String fundNum;

  @ExcelCell(index = 37)
  @ApiModelProperty(value = "是否特定行业")
  private String specialIndustry;

  @ExcelCell(index = 38)
  @ApiModelProperty(value = "是否股东投资人")
  private String isInvestor;

  @ExcelCell(index = 39)
  @ApiModelProperty(value = "是否残疾")
  private String isDisability;

  @ExcelCell(index = 40)
  @ApiModelProperty(value = "是否烈属")
  private String isMatrtyr;

  @ExcelCell(index = 41)
  @ApiModelProperty(value = "是否孤老")
  private String isOld;

  @ExcelCell(index = 42)
  @ApiModelProperty(value = "残疾证号")
  private String disablityNum;

  @ExcelCell(index = 43)
  @ApiModelProperty(value = "烈属证号")
  private String matrtyrNum;

  @ExcelCell(index = 44)
  @ApiModelProperty(value = "电子邮箱")
  private String email;

  @ExcelCell(index = 45)
  @ApiModelProperty(value = "省")
  private String province;

  @ExcelCell(index = 46)
  @ApiModelProperty(value = "城市")
  private String city;

  @ExcelCell(index = 47)
  @ApiModelProperty(value = "区")
  private String area;

  @ExcelCell(index = 48)
  @ApiModelProperty(value = "详细地址")
  private String address;

  @ExcelCell(index = 49)
  @ApiModelProperty(value = "备注")
  private String remark;
}
