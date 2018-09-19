package com.ljwm.gecko.client.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/3 10:52
 */
@Data
@Accessors(chain = true)
@ApiModel("公司信息表单")
public class CompanyForm {

  @ApiModelProperty(value = "企业id")
  private Long companyId;

  @ApiModelProperty(value = "企业名称")
  private String name;

  @ApiModelProperty(value = "企业类型")
  private Integer type;

  @ApiModelProperty(value = "企业纳税代码")
  private String code;

  @ApiModelProperty(value = "手机号码")
  private String phoneNum;

  @ApiModelProperty(value = "证照图片文件, 该部分的filePath入参改为picPath",name = "picPath")
  @JSONField(name = "picPath")
  private String filePath;

  @ApiModelProperty(value = "省级代码")
  private Integer provCode;

  @ApiModelProperty(value = "市级代码")
  private Integer cityCode;

  @ApiModelProperty(value = "区级代码")
  private Integer areaCode;

  @ApiModelProperty(value = "详细地址")
  private String address;

  List<CompanySpecialForm> companySpecialList;
}
