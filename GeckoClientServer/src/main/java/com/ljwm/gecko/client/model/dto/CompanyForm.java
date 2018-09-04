package com.ljwm.gecko.client.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/3 10:52
 */
@Data
@Accessors(chain = true)
@ApiModel("公司信息表单")
public class CompanyForm {

  @ApiModelProperty(value = "企业名称")
  private String name;

  @ApiModelProperty(value = "企业类型")
  private Integer type;

  @ApiModelProperty(value = "企业纳税代码")
  @TableField("`CODE`")
  private String code;

  @ApiModelProperty(value = "证照图片文件")
  private String filePath;

  @ApiModelProperty(value = "省级代码")
  @TableField("`PROV_CODE`")
  private Integer provCode;

  @ApiModelProperty(value = "市级代码")
  @TableField("`CITY_CODE`")
  private Integer cityCode;

  @ApiModelProperty(value = "区级代码")
  @TableField("`AREA_CODE`")
  private Integer areaCode;

  @ApiModelProperty(value = "详细地址")
  @TableField("`ADDRESS`")
  private String address;
}
