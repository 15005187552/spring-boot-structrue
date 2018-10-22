package com.ljwm.gecko.client.model.vo;

import com.ljwm.gecko.base.utils.excelutil.ExcelCell;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/17 16:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PersonExportVo {

  @ExcelCell(index = 0)
  @ApiModelProperty(value = "工号")
  private String jobNum;

  @ExcelCell(index = 1)
  @ApiModelProperty(value = "*姓名")
  private String name;

  @ExcelCell(index = 2)
  @ApiModelProperty(value = "*证照类型")
  private String certificate;

  @ExcelCell(index = 3)
  @ApiModelProperty(value = "*证照号码")
  private String certNum;

  @ExcelCell(index = 4)
  @ApiModelProperty(value = "*国籍(地区)")
  private String country;

  @ExcelCell(index = 5)
  @ApiModelProperty(value = "性别")
  private String gender;

  @ExcelCell(index = 6)
  @ApiModelProperty(value = "出生年月")
  private String birthday;

  @ExcelCell(index = 7)
  @ApiModelProperty(value = "*人员状态")
  private String personState;

  @ExcelCell(index = 8)
  @ApiModelProperty(value = "*是否雇员")
  private String employee;

  @ExcelCell(index = 9)
  @ApiModelProperty(value = "*手机号码")
  private String regMobile;

  @ExcelCell(index = 10)
  @ApiModelProperty(value = "是否残疾")
  private String isDisability;

  @ExcelCell(index = 11)
  @ApiModelProperty(value = "是否烈属")
  private String isMatrtyr;

  @ExcelCell(index = 12)
  @ApiModelProperty(value = "是否孤老")
  private String isOld;

  @ExcelCell(index = 13)
  @ApiModelProperty(value = "残疾证号")
  private String disablityNum;

  @ExcelCell(index = 14)
  @ApiModelProperty(value = "烈属证号")
  private String matrtyrNum;

  @ExcelCell(index = 15)
  @ApiModelProperty(value = "任职受雇日期")
  private String hireDate;

  @ExcelCell(index = 16)
  @ApiModelProperty(value = "离职日期")
  private String termDate;

  @ExcelCell(index = 17)
  @ApiModelProperty(value = "电子邮箱")
  private String email;

  @ExcelCell(index = 18)
  @ApiModelProperty("学历")
  private String education;

  @ExcelCell(index = 19)
  @ApiModelProperty("职业")
  private String job;

  @ExcelCell(index = 20)
  @ApiModelProperty(value = "开户银行")
  private String bank;

  @ExcelCell(index = 21)
  @ApiModelProperty(value = "银行账号")
  private String bankNum;

  @ExcelCell(index = 22)
  @ApiModelProperty(value = "是否特定行业")
  private String specialIndustry;

  @ExcelCell(index = 23)
  @ApiModelProperty(value = "是否股东、投资者")
  private String isInvestor;

  @ExcelCell(index = 24)
  @ApiModelProperty(value = "个人股本（投资）额")
  private String investMoney;

  @ExcelCell(index = 25)
  @ApiModelProperty(value = "户籍所在省份")
  private String familyProvince;

  @ExcelCell(index = 26)
  @ApiModelProperty(value = "户籍所在城市")
  private String familyCity;

  @ExcelCell(index = 27)
  @ApiModelProperty(value = "户籍所在区（县）")
  private String familyArea;

  @ExcelCell(index = 28)
  @ApiModelProperty("户籍所在详细地址")
  private String familyAddress;

  @ExcelCell(index = 29)
  @ApiModelProperty(value = "居住省份")
  private String province;

  @ExcelCell(index = 30)
  @ApiModelProperty(value = "居住城市")
  private String city;

  @ExcelCell(index = 31)
  @ApiModelProperty(value = "居住所在区（县）")
  private String area;

  @ExcelCell(index = 32)
  @ApiModelProperty("居住详细地址")
  private String address;

  @ExcelCell(index = 33)
  @ApiModelProperty("备注")
  private String remark;

  @ExcelCell(index = 34)
  @ApiModelProperty("是否境外人员")
  private String isForeign;

  @ExcelCell(index = 35)
  @ApiModelProperty("姓名（中文）")
  private String chineseName;

  @ExcelCell(index = 36)
  @ApiModelProperty("境内有无住所")
  private String isHasHouse;

  @ExcelCell(index = 37)
  @ApiModelProperty("境外纳税人识别号")
  private String taxPayNum;

  @ExcelCell(index = 38)
  @ApiModelProperty("出生地")
  private String birthAddress;

  @ExcelCell(index = 39)
  @ApiModelProperty("首次入境时间")
  private String firstEnterTime;

  @ExcelCell(index = 40)
  @ApiModelProperty("来华时间")
  private String comeTime;

  @ExcelCell(index = 41)
  @ApiModelProperty("任职期限")
  private String employeeDuration;

  @ExcelCell(index = 42)
  @ApiModelProperty("预计离境时间")
  private String leaveTime;

  @ExcelCell(index = 43)
  @ApiModelProperty("预计离境地点")
  private String leaveAdderss;

  @ExcelCell(index = 44)
  @ApiModelProperty("境内职务")
  private String inPost;

  @ExcelCell(index = 45)
  @ApiModelProperty("境外职务")
  private String outPost;

  @ExcelCell(index = 46)
  @ApiModelProperty("支付地")
  private String payAdderss;

  @ExcelCell(index = 47)
  @ApiModelProperty("境外支付地（国别/地区）")
  private String outPayAdderss;

  @ExcelCell(index = 48)
  @ApiModelProperty("*境内任职受雇单位名称")
  private String inCompany;

  @ExcelCell(index = 49)
  @ApiModelProperty("*境内任职受雇单位扣缴义务人编码")
  private String inCompanyCode;

  @ExcelCell(index = 50)
  @ApiModelProperty("*境内任职受雇单位地址")
  private String inCompanyAddress;

  @ExcelCell(index = 51)
  @ApiModelProperty("*境内任职受雇单位邮政编码")
  private String inCompanyPostCode;

}
